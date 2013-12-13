package com.android.lp.vote;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.lp.communication.IReactor;
import com.android.lp.communication.JSONReceiveTask;
import com.android.lp.vote.models.Keyword;
import com.android.lp.vote.models.Survey;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ShowByKeyword extends Activity implements IReactor{
	
	public final int GET_CODE = 4;

	private ListView listViewSurveys;
	private String keywordsFromInput;
	
	private ProgressDialog pDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_by_keyword);
		
		listViewSurveys = (ListView) findViewById(R.id.act_show_by_kwrds_mySurveysListView);
		
		this.keywordsFromInput =  getIntent().getStringExtra("KEYWORDS_INPUT");
		
		Keyword[] parsedKeywords = parseInputFromSearchField();
		
		if( parsedKeywords.length <= 0 ){
			
			Toast.makeText(getApplicationContext(), "No user input has been parsed",
					Toast.LENGTH_SHORT).show();
			
			//TODO: go back to "main" activity
			return;
		}
		
		startActivityRetrieveSurveysByKeyWords(parsedKeywords);		
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_by_keyword, menu);
		return true;
	}
	
	private Keyword[] parseInputFromSearchField() {

		String[] inputExploded = keywordsFromInput.split("\\s+");
		
		Keyword[] parsedKeywords = new Keyword[inputExploded.length];
		
		for (int i = 0; i < inputExploded.length; i++) {
			// survey id we do not know, that's why default value -1
			parsedKeywords[i] = new Keyword( inputExploded[i], Long.valueOf("-1") );
		}
		
		return parsedKeywords;
	}	

	private void startActivityRetrieveSurveysByKeyWords(Keyword[] keyWords) {
		
		StringBuilder dynamicUrl = new StringBuilder(
				"http://puwel.sk/vote/index.php/api/survey_c/surveys_by_keywords");

		for (int i = 0; i < keyWords.length; i++) {

			Keyword singleKeyword = keyWords[i];

			if (i == 0) {
				dynamicUrl.append("?");
			} else {
				dynamicUrl.append("&");
			}
			dynamicUrl.append("keywords_names[]=");
			dynamicUrl.append(singleKeyword.getK_name());
		}

		dynamicUrl.append("&format=json");

		System.out.println(dynamicUrl.toString());
		Toast.makeText(getApplicationContext(), dynamicUrl.toString(),
				Toast.LENGTH_SHORT).show();

		JSONReceiveTask task = new JSONReceiveTask(this);
		task.execute(new String[] { dynamicUrl.toString() });
	}
	
	@Override
	public void onPostExecute(String result) {
		
		pDialog.dismiss();
		
		System.out.println(result);
//		Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT)
//				.show();
		
		try {
			final List<Map<String, Survey>> mySurveysList = new ArrayList<Map<String, Survey>>();
			
			JSONObject jsonResponse = new JSONObject(result);

			String status = jsonResponse.optString("v_status");

			System.out.println(status);
//			Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT)
//					.show();

			if (!status.equalsIgnoreCase("OK")) {
				throw new JSONException("Something sucked on server side: "
						+ jsonResponse.optString("v_message"));
			}

			JSONArray keywordsJsonArray = jsonResponse.optJSONArray("v_data");

			for (int i = 0; i < keywordsJsonArray.length(); i++) {
				JSONObject keywordJsonObject = keywordsJsonArray
						.getJSONObject(i);

				Long single_id = Long.parseLong(keywordJsonObject
						.optString("s_id"));
				Long single_creator = Long.parseLong(keywordJsonObject
						.optString("s_creator"));
				String single_title = keywordJsonObject.optString("s_title");
				String single_type = keywordJsonObject.optString("s_type");
				String single_start_time = keywordJsonObject
						.optString("s_start_time");
				String single_end_time = keywordJsonObject
						.optString("s_end_time");
				String single_hash_or_url = keywordJsonObject
						.optString("s_hash_or_url");

				Survey.SURVEY_TYPE s_type = (single_type
						.equalsIgnoreCase(Survey.SURVEY_TYPE.GLOBAL.name()) ? Survey.SURVEY_TYPE.GLOBAL
						: Survey.SURVEY_TYPE.LOCAL);

				Survey singleRetrievedSurvey = new Survey(single_id, single_creator,
						single_title, s_type, single_start_time,
						single_end_time, single_hash_or_url);
				
				mySurveysList.add(createSurveyEntry("survey_title", singleRetrievedSurvey));

				System.out.println(singleRetrievedSurvey.toFullString());
//				Toast.makeText(getApplicationContext(), singleRetrievedSurvey.toString(),
//						Toast.LENGTH_SHORT).show();
				
				final SimpleAdapter simpleAdapter = new SimpleAdapter(this,
						mySurveysList, android.R.layout.simple_list_item_1,
						new String[] { "survey_title" },
						new int[] { android.R.id.text1 });

				listViewSurveys.setAdapter(simpleAdapter);

				listViewSurveys.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, final View view,
							int position, long id) {
						
						@SuppressWarnings("unchecked")
						HashMap<String, Survey> selectedItem = (HashMap<String, Survey>) parent
								.getItemAtPosition(position);

						Survey selectedSurvey = (Survey) selectedItem
								.get("survey_title");
						
						showSurveyDetail(selectedSurvey);
					}

				});				
			}
		} catch (JSONException e) {
			Toast.makeText(getApplicationContext(), "Error" + e.toString(),
					Toast.LENGTH_SHORT).show();
		}

	}
	
	private HashMap<String, Survey> createSurveyEntry(String surveyTitle,
			Survey singleEntry) {
		HashMap<String, Survey> surveyMappingMap = new HashMap<String, Survey>();
		surveyMappingMap.put(surveyTitle, singleEntry);
		return surveyMappingMap;
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

	@Override
	public void onPreExecute() {
		pDialog = new ProgressDialog(ShowByKeyword.this);
		pDialog.setMessage("...searching for surveys...");
		pDialog.setIndeterminate(false);
	    pDialog.setCancelable(true);
	    pDialog.show();
	}	
}
