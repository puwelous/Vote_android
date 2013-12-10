package com.android.lp.vote;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.lp.vote.helpers.AssetsPropertyReader;

public class ActivityMySurvey extends Activity {

	private AssetsPropertyReader assetsPropertyReader = new AssetsPropertyReader(this);
	
	private String jsonResult;
	private String url = "";
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_survey);

		// initializing property reader
		Properties properties;
		try {
			properties = assetsPropertyReader.getProperties("RESTCallURLs.properties");
			url = properties.getProperty("surveys_all");
			
		} catch (IOException e) {
			Log.e( getClass().getName(), e.getLocalizedMessage() );
			Toast.makeText(getApplicationContext(),
					e.getLocalizedMessage(), Toast.LENGTH_LONG).show();				
		}

		Toast.makeText(getApplicationContext(),
				url, Toast.LENGTH_LONG).show();		
		
		Button ShowActivityCreateSurveyButton = (Button) findViewById(R.id.btn_NewSurvey);
		ShowActivityCreateSurveyButton
				.setOnClickListener(new View.OnClickListener() {

					public void onClick(View view) {
						startActivityCreateSurvey();
					}
				});

		listView = (ListView) findViewById(R.id.mySurveysListView);

		accessWebService();

	}

	public void accessWebService() {
		JsonReadTask task = new JsonReadTask();
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

	// Async Task to access the web
	private class JsonReadTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {

			HttpClient httpclient = new DefaultHttpClient();

			// Log.i("MyActivity", "params[0]:" + params[0]);
			// HttpPost httppost = new HttpPost(params[0]);

			HttpGet httpget = new HttpGet(params[0]);
			try {

				// System.out.println(httppost.getURI().toString());
				// System.out.println(httpget.getURI().toString());

				// HttpResponse response = httpclient.execute(httppost);
				HttpResponse response = httpclient.execute(httpget);
				jsonResult = inputStreamToString(
						response.getEntity().getContent()).toString();
			}

			catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		private StringBuilder inputStreamToString(InputStream is) {
			String rLine = "";
			StringBuilder answer = new StringBuilder();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));

			try {
				while ((rLine = rd.readLine()) != null) {
					answer.append(rLine);
				}
			}

			catch (IOException e) {
				// e.printStackTrace();
				Toast.makeText(getApplicationContext(),
						"Error..." + e.toString(), Toast.LENGTH_LONG).show();
			}
			return answer;
		}

		@Override
		protected void onPostExecute(String result) {
			ListDrawer();
		}
	}// end async task

	// build hash set for list view
	public void ListDrawer() {
		List<Map<String, String>> mySurveysList = new ArrayList<Map<String, String>>();

		try {

			JSONObject jsonResponse = new JSONObject(jsonResult);
			// JSONArray jsonMainNode = jsonResponse.optJSONArray("emp_info");
			JSONArray jsonMainNode = jsonResponse.optJSONArray("v_data");

			// System.out.print(jsonMainNode);
			// Toast.makeText(getApplicationContext(),
			// jsonResult == null ? "NULL!" : "Haha " + jsonMainNode.toString(),
			// Toast.LENGTH_LONG).show();
			//
			// if (jsonResult == null || jsonResult != null)
			// return;

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

}
