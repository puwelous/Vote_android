package com.android.lp.vote;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.lp.communication.IReactor;
import com.android.lp.communication.JSONReceiveTask;
import com.android.lp.vote.models.Keyword;
import com.android.lp.vote.models.Survey;

public class ActivitySurveysByKeywords extends Activity implements IReactor {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_surveys_by_keywords);

		((Button) findViewById(R.id.button_test_delete_me_later))
				.setOnClickListener(new View.OnClickListener() {

					public void onClick(View view) {

						Keyword[] keyWords;

						keyWords = parseInputFromSearchField();

						if( keyWords.length <= 0 ){
							
							Toast.makeText(getApplicationContext(), "No user input has been parsed",
									Toast.LENGTH_SHORT).show();
							return;
						}
							
						
						startActivityRetrieveSurveysByKeyWords(keyWords);
					}
				});
	}

	private Keyword[] parseInputFromSearchField() {

		String[] inputExploded = "product crocodile".split("\\s+");
		
		Keyword[] parsedKeywords = new Keyword[inputExploded.length];
		
		for (int i = 0; i < inputExploded.length; i++) {
			// survey id we do not know, that's why default value -1
			parsedKeywords[i] = new Keyword( inputExploded[i], Long.valueOf("-1") );
		}
		
		return parsedKeywords;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_surveys_by_keywords, menu);
		return true;
	}

	private void startActivityRetrieveSurveysByKeyWords(Keyword[] keyWords) {
		// http://puwel.sk/vote/index.php/api/survey_c/surveys_by_keywords?keywords_names[]=product&keywords_names[]=Gg
		StringBuilder sb = new StringBuilder(
				"http://puwel.sk/vote/index.php/api/survey_c/surveys_by_keywords");

		for (int i = 0; i < keyWords.length; i++) {

			Keyword singleKeyword = keyWords[i];

			if (i == 0) {
				sb.append("?");
			} else {
				sb.append("&");
			}
			sb.append("keywords_names[]=");
			sb.append(singleKeyword.getK_name());
		}

		sb.append("&format=json");

		System.out.println(sb.toString());
		Toast.makeText(getApplicationContext(), sb.toString(),
				Toast.LENGTH_SHORT).show();

		JSONReceiveTask task = new JSONReceiveTask(this);
		task.execute(new String[] { sb.toString() });

	}

	@Override
	public void reactOnResult(String result) {
		// TODO!
		System.out.println(result);
		Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT)
				.show();

		try {

			JSONObject jsonResponse = new JSONObject(result);

			String status = jsonResponse.optString("v_status");

			System.out.println(status);
			Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT)
					.show();

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

				Survey newSurvey = new Survey(single_id, single_creator,
						single_title, s_type, single_start_time,
						single_end_time, single_hash_or_url);

				System.out.println(newSurvey.toFullString());
				Toast.makeText(getApplicationContext(), newSurvey.toString(),
						Toast.LENGTH_SHORT).show();

			}
		} catch (JSONException e) {
			Toast.makeText(getApplicationContext(), "Error" + e.toString(),
					Toast.LENGTH_SHORT).show();
		}

	}

}
