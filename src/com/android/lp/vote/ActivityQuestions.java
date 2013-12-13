package com.android.lp.vote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.lp.communication.IReactor;
import com.android.lp.communication.JSONReceiveTask;
import com.android.lp.communication.JSONSendTask;
import com.android.lp.communication.messages.QuestionsAnswersMessageCreate;
import com.android.lp.helpers.AssetsPropertyReader;
import com.android.lp.vote.models.Answer;
import com.android.lp.vote.models.Question;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityQuestions extends Activity implements IReactor{
	
	public static final int GET_CODE = 3;
	
	private AssetsPropertyReader assetsPropertyReader = null;

	private Long survey_id;
	private List<Question> surveyQuestions = new ArrayList<Question>();

	private String urlQuestionsAnswersCreate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_questions);

		// initializing property reader
		Properties properties;
		try {
			assetsPropertyReader = new AssetsPropertyReader(this);
			properties = assetsPropertyReader
					.getProperties("RESTCallURLs.properties");
			urlQuestionsAnswersCreate = properties.getProperty("quesions_and_answers_create");

		} catch (IOException e) {
			Log.e(getClass().getName(), e.getLocalizedMessage());
			Toast.makeText(getApplicationContext(), e.getLocalizedMessage(),
					Toast.LENGTH_LONG).show();
		}		
		
		
		// retrieve new survey ID from extras
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			survey_id = extras.getLong("NEW_SURVEY_ID");
			Toast.makeText(getApplicationContext(), "Survey ID: " + survey_id,
					Toast.LENGTH_LONG).show();			
		}
		
			if( surveyQuestions.isEmpty() ){
			((TextView) findViewById(R.id.tv_questionsss)).setText("List of all questions created, if none is created, then empty");
		}else{
			//TODO: String -> initialize list!
			((TextView) findViewById(R.id.tv_questionsss)).setText( surveyQuestions.toString()  );
		}

		((Button) findViewById(R.id.btn_AddQuest))
				.setOnClickListener(new View.OnClickListener() {

					public void onClick(View view) {
						startActivityAddQuest();
					}
				});

		((Button) findViewById(R.id.btn_activity_questions_create))
				.setOnClickListener(new View.OnClickListener() {

					public void onClick(View view) {
						accessWebServiceCreateQuestion();
					}


				});
	}

	/**
	 * Start activity for creating new question
	 */
	private void startActivityAddQuest() {
		Intent intentAddNewQuestion = new Intent(this, ActivityAddQuestion.class);
		intentAddNewQuestion.putExtra("NEW_SURVEY_ID", this.survey_id);
		startActivityForResult(intentAddNewQuestion, GET_CODE);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
		if (requestCode == GET_CODE && resultCode == RESULT_OK) {
		
			Question newQuestion = (Question) intent.getSerializableExtra("NEW_QUESTION");
			
			System.out.println("Passed question: " + newQuestion.toFullString() );
			Toast.makeText(getApplicationContext(), newQuestion.toFullString(), Toast.LENGTH_LONG).show();			
			
			surveyQuestions.add( newQuestion);
			
			((TextView) findViewById(R.id.tv_questionsss)).setText( surveyQuestions.toString() );
		}
	}	

//	private void startActivityMySurvey() {
//		Intent startTwo = new Intent(this, ActivityMySurvey.class);
//		startActivity(startTwo);
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_new_questions, menu);
		return true;
	}

	
	private void accessWebServiceCreateQuestion() {
		
		/*************** prepare data ******************/
		// common json message object
		JSONObject clientJSONMessage = new JSONObject();
		
		try {	
			clientJSONMessage = new QuestionsAnswersMessageCreate(this.surveyQuestions).toJson();
			
			//System.out.println("by toString() " + clientJSONMessage.toString() );
			
			JSONSendTask task = new JSONSendTask(this);
			task.execute(new String[] { this.urlQuestionsAnswersCreate , clientJSONMessage.toString() });				
			
		} catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			System.err.println( e.getMessage() );
			return;
		}
	}
	



	@Override
	public void reactOnResult(String result) {
		
		// TODO: Handle result
		try {
			JSONObject message = new JSONObject(result);

			Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();	
			System.out.println("Result ActivityQuestions:\n" + result);
			
			String status = message.getString("v_status");
			if(status.equalsIgnoreCase("OK")){
				// switch to my surveys screen
				Intent intentAddNewQuestion = new Intent(this, ActivityMySurvey.class);
				startActivity(intentAddNewQuestion);
			}else{
				throw new JSONException("Status: " + status + ", result" + result);
			}

		} catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), e.getMessage(),
					Toast.LENGTH_LONG).show();
			Toast.makeText(getApplicationContext(), "Result: " + result,
					Toast.LENGTH_LONG).show();	
			System.err.println(e.getMessage());
			System.err.println("Result: " + result);
			// prevent starting new activity!
			return;
		}
		
	}	
}
