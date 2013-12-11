package com.android.lp.vote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.lp.communication.IReactor;
import com.android.lp.communication.JSONReceiveTask;
import com.android.lp.helpers.AssetsPropertyReader;

public class ActivityMySurvey extends Activity implements IReactor {

	private AssetsPropertyReader assetsPropertyReader = null;

	// private String jsonResult;
	private String url = "";
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_survey);

		// initializing property reader
		Properties properties;
		try {
			assetsPropertyReader = new AssetsPropertyReader(
					this);
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
		List<Map<String, String>> mySurveysList = new ArrayList<Map<String, String>>();

		try {

			JSONObject jsonResponse = new JSONObject(jsonResult);
			JSONArray jsonMainNode = jsonResponse.optJSONArray("v_data");

			for (int i = 0; i < jsonMainNode.length(); i++) {
				JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
				String single_title = jsonChildNode.optString("s_title");
				String singleEntry = single_title;

				mySurveysList.add(createSurveyEntry("surveys", singleEntry));
			}
		} catch (JSONException e) {
			Toast.makeText(getApplicationContext(), "Error" + e.toString(),
					Toast.LENGTH_SHORT).show();
		}

		SimpleAdapter simpleAdapter = new SimpleAdapter(this, mySurveysList,
				android.R.layout.simple_list_item_1,
				new String[] { "surveys" }, new int[] { android.R.id.text1 });
		listView.setAdapter(simpleAdapter);
	}

	private HashMap<String, String> createSurveyEntry(String title,
			String singleEntry) {
		HashMap<String, String> surveyMappingMap = new HashMap<String, String>();
		surveyMappingMap.put(title, singleEntry);
		return surveyMappingMap;
	}

	@Override
	public void reactOnResult(String result) {
		ListDrawer(result);
	}

}