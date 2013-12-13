package com.android.lp.vote;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.lp.communication.IReactor;
import com.android.lp.communication.JSONReceiveTask;
import com.android.lp.helpers.AssetsPropertyReader;
import com.android.lp.vote.models.Survey;

public class ActivityMySurvey extends Activity implements IReactor {
	
	public static final int GET_CODE = 2;

	private AssetsPropertyReader assetsPropertyReader = null;

	private String url = "";
	private ListView listView;
	
	private ProgressDialog pDialog;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_survey);

		// initializing property reader
		Properties properties;
		try {
			assetsPropertyReader = new AssetsPropertyReader(this);
			properties = assetsPropertyReader
					.getProperties("RESTCallURLs.properties");
			url = properties.getProperty("surveys_all");

		} catch (IOException e) {
			Log.e(getClass().getName(), e.getLocalizedMessage());
			Toast.makeText(getApplicationContext(), e.getLocalizedMessage(),
					Toast.LENGTH_LONG).show();
		}

		((Button) findViewById(R.id.btn_NewSurvey))
				.setOnClickListener(new View.OnClickListener() {

					public void onClick(View view) {
						startActivityCreateSurvey();
					}
				});

		listView = (ListView) findViewById(R.id.mySurveysListView);

		accessWebService();

	}

	public void accessWebService() {
		JSONReceiveTask task = new JSONReceiveTask(this);
		task.execute(new String[] { url });
	}

	private void startActivityCreateSurvey() {
		Intent startTwo = new Intent(this, ActivityNewSurvey.class);
		startActivity(startTwo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_survey, menu);
		return true;
	}

	// build hash set for list view
	public void ListDrawer(String jsonResult) {
		final List<Map<String, Survey>> mySurveysList = new ArrayList<Map<String, Survey>>();

		try {

			JSONObject jsonResponse = new JSONObject(jsonResult);
			JSONArray jsonMainNode = jsonResponse.optJSONArray("v_data");

			for (int i = 0; i < jsonMainNode.length(); i++) {
				JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

				Long single_id = Long
						.parseLong(jsonChildNode.optString("s_id"));
				Long single_creator = Long.parseLong(jsonChildNode
						.optString("s_creator"));
				String single_title = jsonChildNode.optString("s_title");
				String single_type = jsonChildNode.optString("s_type");
				String single_start_time = jsonChildNode
						.optString("s_start_time");
				String single_end_time = jsonChildNode.optString("s_end_time");
				String single_hash_or_url = jsonChildNode
						.optString("s_hash_or_url");

				Survey.SURVEY_TYPE s_type = (single_type
						.equalsIgnoreCase(Survey.SURVEY_TYPE.GLOBAL.name()) ? Survey.SURVEY_TYPE.GLOBAL
						: Survey.SURVEY_TYPE.LOCAL);

				Survey newSurvey = new Survey(single_id, single_creator,
						single_title, s_type, single_start_time,
						single_end_time, single_hash_or_url);

				System.out.println(newSurvey);

				mySurveysList.add(createSurveyEntry("survey_title", newSurvey));
			}
		} catch (JSONException e) {
			Toast.makeText(getApplicationContext(), "Error" + e.toString(),
					Toast.LENGTH_SHORT).show();
		}

		final SimpleAdapter simpleAdapter = new SimpleAdapter(this,
				mySurveysList, android.R.layout.simple_list_item_1,
				new String[] { "survey_title" },
				new int[] { android.R.id.text1 });

		listView.setAdapter(simpleAdapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				// final String item = (String)
				// parent.getItemAtPosition(position);
				@SuppressWarnings("unchecked")
				HashMap<String, Survey> selectedItem = (HashMap<String, Survey>) parent
						.getItemAtPosition(position);

				// System.out.println("toString() !!! " +
				// selectedItem.toString());
				// System.out.println("selitem.get() !!! " +
				// selectedItem.get("survey_title").toFullString());

				Survey selectedSurvey = (Survey) selectedItem
						.get("survey_title");
				
				showSurveyDetail(selectedSurvey);
			}

		});

	}

	private HashMap<String, Survey> createSurveyEntry(String surveyTitle,
			Survey singleEntry) {
		HashMap<String, Survey> surveyMappingMap = new HashMap<String, Survey>();
		surveyMappingMap.put(surveyTitle, singleEntry);
		return surveyMappingMap;
	}

	@Override
	public void onPostExecute(String result) {
		pDialog.dismiss();
		ListDrawer(result);
	}
	
	public void showSurveyDetail(Survey survey){
		// put survey ID into shared preferences
		
		Intent detailSurveyIntent = new Intent(this,
				DetailSurveyView.class);
//		intent_ActivityQuestions.putExtra("NEW_SURVEY_ID", survey.getS_id());

//		startActivityForResult(intent_ActivityQuestions, GET_CODE);		
		
//		detailSurveyIntent.putExtra("SURVEY_TITLE", survey.getS_title());
//		detailSurveyIntent.putExtra("SURVEY_CREATOR", survey.getS_creator());
//		detailSurveyIntent.putExtra("SURVEY_START_TIME", survey.getS_start_time());
//		detailSurveyIntent.putExtra("SURVEY_END_TIME", survey.getS_end_time());
//		detailSurveyIntent.putExtra("SURVEY_TYPE", survey.getS_type().name());
//		detailSurveyIntent.putExtra("SURVEY_HASH_OR_URL", survey.getS_hash_or_url());
		
		detailSurveyIntent.putExtra("SINGLE_SURVEY", (Serializable)survey);
		
		startActivityForResult(detailSurveyIntent, GET_CODE);
	}
	
	/*
	 * TODO: react on result!
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (requestCode == GET_CODE && resultCode == RESULT_OK) {
			Toast.makeText(getApplicationContext(), "SerializableData: SoFarNothing", //+ data.getSerializableExtra(name),
					Toast.LENGTH_LONG).show();				
		}
	}

	@Override
	public void onPreExecute() {
		pDialog = new ProgressDialog(ActivityMySurvey.this);
		pDialog.setMessage("...loading your surveys...");
		pDialog.setIndeterminate(false);
	    pDialog.setCancelable(true);
	    pDialog.show();
	}	

}