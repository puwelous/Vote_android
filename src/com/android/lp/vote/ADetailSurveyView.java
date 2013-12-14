package com.android.lp.vote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.lp.communication.IReactor;
import com.android.lp.communication.JSONReceiveTask;
import com.android.lp.communication.JSONSendTask;
import com.android.lp.helpers.AssetsPropertyReader;
import com.android.lp.vote.models.Answer;
import com.android.lp.vote.models.AnswerData;
import com.android.lp.vote.models.Question;
import com.android.lp.vote.models.Question.QUESTION_TYPE;
import com.android.lp.vote.models.Survey;
import com.android.lp.vote.models.SurveyData;
import com.google.gson.Gson;

public class ADetailSurveyView extends Activity implements IReactor {

	private AssetsPropertyReader assetsPropertyReader = null;

	private Survey detailSurvey = null;

	private String urlLoad = null;
	private String urlSend = null;
	
	
	private ProgressDialog pDialog;
	
	Set<Question> questionsArray = null;
	
	List<AnswerData> answerData = null;	
	
	private enum CALL_TYPE{
		LOAD_QUESTIONS, SEND_ANSWERS
	}
	
	private CALL_TYPE CALL_TYPE_OF_JSON = CALL_TYPE.LOAD_QUESTIONS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_survey_view);

		CALL_TYPE_OF_JSON = CALL_TYPE.LOAD_QUESTIONS;
		
		Properties properties;
		try {
			assetsPropertyReader = new AssetsPropertyReader(this);
			properties = assetsPropertyReader
					.getProperties("RESTCallURLs.properties");
			urlLoad = properties.getProperty("quesions_and_answers_by_survey");
			urlSend = properties.getProperty("survey_data_create");

		} catch (IOException e) {
			Log.e(getClass().getName(), e.getLocalizedMessage());
			Toast.makeText(getApplicationContext(), e.getLocalizedMessage(),
					Toast.LENGTH_LONG).show();
			setResult(RESULT_OK, getIntent());
			finish();
		}

		((Button) findViewById(R.id.btn_show_questions))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// access web service
						accessWebServiceLoadBySurvey();
					}
				});
		
		((Button) findViewById(R.id.btn_stop_survey))
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// access web service
				prepareSurveyData();
				accessWebServiceCreateAnswersData();
			}
		});		

		((Button) findViewById(R.id.btn_go_back))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						setResult(RESULT_OK, getIntent());
						finish();
					}
				});

		Intent thisIntent = getIntent();

		detailSurvey = (Survey) thisIntent
				.getSerializableExtra("SINGLE_SURVEY");

		if (detailSurvey == null) {
			Toast.makeText(getApplicationContext(), "No survey data found!",
					Toast.LENGTH_LONG).show();
			setResult(RESULT_OK, getIntent());
			finish();
		}

		setSurveyDetails();
	}
	
	private void accessWebServiceLoadBySurvey() {

			JSONReceiveTask task = new JSONReceiveTask(this);

			//http://puwel.sk/vote/index.php/api/survey_c/survey_data_by_id/s_id/1/format/json
			StringBuilder finalUrl = new StringBuilder().append(this.urlLoad).append("/s_id/").append(String.valueOf(this.detailSurvey.getS_id())).append("/format/json");
			
			//System.out.println("Final string: " + finalUrl.toString() );
			
			task.execute(new String[] { finalUrl.toString() } );
	}
	
	private void accessWebServiceCreateAnswersData() {

		JSONSendTask task = new JSONSendTask(this);
		JSONObject clientMessage = new JSONObject();
		JSONArray answersDataJSONArray = new JSONArray();

		try {
			clientMessage.put("v_survey_data", 
					new Gson().toJson( 
							new SurveyData(this.detailSurvey.getS_id(), Long.parseLong("2"))
									)
							);
			
			//answerData
			for(AnswerData singleAnswerData : answerData)
			{
				answersDataJSONArray.put( new Gson().toJson(singleAnswerData) );
			}
			
			clientMessage.put("v_answer_data", answersDataJSONArray);
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), e.getMessage(),
					Toast.LENGTH_LONG).show();			
		}  catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), e.getMessage(),
					Toast.LENGTH_LONG).show();
			System.err.println(e.getMessage());
			// prevent starting new activity!
			return;
		}	
		

		
		task.execute(new String[] { this.urlSend,  clientMessage.toString() } );
}	

	private void setSurveyDetails() {

		((TextView) findViewById(R.id.tv_real_creator_id)).setText(String
				.valueOf(detailSurvey.getS_creator()));

		((TextView) findViewById(R.id.tv_surveys_name)).setText(detailSurvey
				.getS_title());
		((TextView) findViewById(R.id.tv_real_type)).setText(detailSurvey
				.getS_type().name());
		((TextView) findViewById(R.id.tv_real_hash_code)).setText(detailSurvey
				.getS_hash_or_url());

		String timestamp_start[] = detailSurvey.getS_start_time().split("\\s+");

		((TextView) findViewById(R.id.tv_real_start_date))
				.setText(timestamp_start[0]);
		((TextView) findViewById(R.id.tv_real_start_time))
				.setText(timestamp_start[1]);

		String timestamp_end[] = detailSurvey.getS_end_time().split("\\s+");

		((TextView) findViewById(R.id.tv_real_end_date))
				.setText(timestamp_end[0]);
		((TextView) findViewById(R.id.tv_real_end_time))
				.setText(timestamp_end[1]);
	}

	@Override
	public void onPostExecute(String result) {
		
		pDialog.dismiss();
	    
	    System.out.println("Complete result:");
	    System.out.println(result);

	    if( this.CALL_TYPE_OF_JSON == CALL_TYPE.LOAD_QUESTIONS ){
	    	handleResultAfterLoadingQuestions(result);
	    	this.CALL_TYPE_OF_JSON = CALL_TYPE.SEND_ANSWERS;
	    }else{
	    	handleResultAfterSendingAnswers(result);
	    	this.CALL_TYPE_OF_JSON = CALL_TYPE.LOAD_QUESTIONS;
	    }
	}

	private void handleResultAfterSendingAnswers(String result) {
		Toast.makeText(getApplicationContext(), "Thank you!",
				Toast.LENGTH_LONG).show();
		setResult(RESULT_OK, new Intent());
		finish();
	}

	private void handleResultAfterLoadingQuestions(String result) {
		JSONObject message;

		try {
			message = new JSONObject(result);
			
			String v_status = message.getString("v_status");
			if( v_status.equalsIgnoreCase("BAD")){
				
				try{
					String v_message = message.getString("v_message");
					System.out.println(v_message);
					Toast.makeText(getApplicationContext(), v_message,
							Toast.LENGTH_LONG).show();
					Toast.makeText(getApplicationContext(), "Survey not created!",
							Toast.LENGTH_LONG).show();
					// prevent starting new activity!
					return;
				}catch(Exception ee){
					System.out.println(ee.getMessage());
					Toast.makeText(getApplicationContext(), ee.getMessage(),
							Toast.LENGTH_LONG).show();
					Toast.makeText(getApplicationContext(), "Survey not created!",
							Toast.LENGTH_LONG).show();
					// prevent starting new activity!
					return;
				}			
				
			}
			
			JSONObject dataObject = message.getJSONObject("v_data");
			JSONArray questionsJSONArray = dataObject.getJSONArray("v_questions");
			JSONArray answersJSONArray = dataObject.getJSONArray("v_answers");

			questionsArray = new TreeSet<Question>();
			Set<Answer> answersList = new TreeSet<Answer>();
			//Answer[] answersArray = new Answer[answersJSONArray.length()];
			
			for (int i = 0; i < questionsJSONArray.length(); i++) {
				JSONObject questionJSONObject = questionsJSONArray.getJSONObject(i);
				Question singleQuestion= null;
				
				singleQuestion = new Gson().fromJson(questionJSONObject.toString(), Question.class);
				if( questionJSONObject.getLong("q_type") == 1 ){
					singleQuestion.setQ_type(QUESTION_TYPE.RADIO);
				}else if( questionJSONObject.getLong("q_type") == 2 ){
					singleQuestion.setQ_type(QUESTION_TYPE.CHECKBOX);
				}else{
					singleQuestion.setQ_type(QUESTION_TYPE.RAW);
				}
				
				questionsArray.add( singleQuestion );
			}
			
			System.out.println("+++" + questionsArray.toString() + "+++");			
			
			for (int j = 0; j < answersJSONArray.length(); j++) {
				JSONObject answerJSONObject = answersJSONArray.getJSONObject(j);
				Answer singleAnswer = null;
				
				singleAnswer = new Gson().fromJson(answerJSONObject.toString(), Answer.class);
				
				answersList.add(singleAnswer);
				
				//questionsArray.get( singleAnswer.getA_question() ).getAnswers().add(singleAnswer);
				
				//System.out.println( singleAnswer.toFullString() );
				
			}
			
			for (Answer answer : answersList) {
				Iterator<Question> qIterator = questionsArray.iterator();
				
				while( qIterator.hasNext() ){
					Question sq = qIterator.next();
					
					if(  sq.getQ_id().longValue() == answer.getA_question().longValue()    ){
						sq.getAnswers().add(answer);
					}
				}
			}
			
			System.out.println("***********************************");
			System.out.println( questionsArray.toString() );
			System.out.println("***********************************");				
			
			updateUI();

		} catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), e.getMessage(),
					Toast.LENGTH_LONG).show();
//			Toast.makeText(getApplicationContext(), "Result: " + result,
//					Toast.LENGTH_LONG).show();	
			System.err.println(e.getMessage());
			System.err.println("Result: " + result);
			// prevent starting new activity!
			return;
		}	    
	}
	
	private void updateUI() {
		
		LinearLayout allQuestionsWrapper = ((LinearLayout) findViewById(R.id.act_detail_survey_ll_all_question_wrapper));
		
		for (Question question : this.questionsArray)
		{
			

		
		
		// create single question layout
//        <LinearLayout
//        android:id="@+id/act_detail_survey_ll_single_question_wrapper"
//DONE         android:layout_width="match_parent"
//DONE         android:layout_height="wrap_content"
//DONE        android:orientation="vertical" >
		
		LinearLayout singleQuestionWrapper = new LinearLayout(getApplicationContext());
		
		singleQuestionWrapper.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		singleQuestionWrapper.setOrientation(LinearLayout.VERTICAL);

//		<TextView
//        android:id="@+id/textView1"
//DONE        android:layout_width="match_parent"
//DONE        android:layout_height="wrap_content"
//DONE        android:text="Which colour out of following is your favourite one?"
//        android:textAppearance="?android:attr/textAppearanceMedium" />
		
		
		TextView textViewPerQuestion = new TextView(getApplicationContext());
		

		textViewPerQuestion.setLayoutParams( new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		textViewPerQuestion.setText( question.getQ_question()  );
		textViewPerQuestion.setTextColor(Color.BLACK);
		
//        <LinearLayout
//        android:id="@+id/act_detail_survey_ll_all_answers_per_question_wrapper"
//DONE        android:layout_width="match_parent"
//DONE        android:layout_height="wrap_content"
//DONE        android:orientation="vertical" >
		
		LinearLayout answersPerQuestionWrapper = new LinearLayout(getApplicationContext());
		answersPerQuestionWrapper.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		answersPerQuestionWrapper.setOrientation(LinearLayout.VERTICAL);		
		
//        <RadioGroup
//        android:id="@+id/radioGroup1"
//DONE        android:layout_width="match_parent"
//DONE        android:layout_height="wrap_content" >
		
		RadioGroup radioGroupPerQuestion = null;
		
		if( question.getQ_type() == QUESTION_TYPE.RADIO )
		{
			radioGroupPerQuestion = new RadioGroup(getApplicationContext());
			radioGroupPerQuestion.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}
		
		//radio button per answer
		///////////////////////////////// for
		
		for(Answer singleAnswer : question.getAnswers())
		{
			if( question.getQ_type() == QUESTION_TYPE.RADIO )
			{
//        <RadioButton
//        android:id="@+id/radio0"
//DONE        android:layout_width="match_parent"
//DONE       android:layout_height="wrap_content"
//DONE        android:checked="true"
//DONE        android:text="RadioButton" />		
			RadioButton radioButtonPerAnswer = new RadioButton(getApplicationContext());
			radioButtonPerAnswer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			radioButtonPerAnswer.setSelected(true);
			radioButtonPerAnswer.setText( singleAnswer.getA_answer() );
			radioButtonPerAnswer.setTextColor(Color.BLACK);
		
			// adding radio button to radio button group
			radioGroupPerQuestion.addView(radioButtonPerAnswer);
			}else if( question.getQ_type() == QUESTION_TYPE.CHECKBOX ){
//                <CheckBox
//                android:id="@+id/checkBox1"
//DONE                android:layout_width="match_parent"
//DONE                android:layout_height="wrap_content"
//DONE                android:text="CheckBox" />				
				CheckBox checkBoxPerAnswer = new CheckBox(getApplicationContext());
				checkBoxPerAnswer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				checkBoxPerAnswer.setSelected(false);
				checkBoxPerAnswer.setText( singleAnswer.getA_answer() );
				checkBoxPerAnswer.setTextColor(Color.BLACK);
				answersPerQuestionWrapper.addView(checkBoxPerAnswer);
			}else if( question.getQ_type() == QUESTION_TYPE.RAW ){
//                <EditText
//                android:id="@+id/editText1"
//                android:layout_width="match_parent"
//                android:layout_height="wrap_content"
//                android:ems="10" >
//            </EditText>
				EditText rawAnswer = new EditText(getApplicationContext());
				rawAnswer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				answersPerQuestionWrapper.addView(rawAnswer);
			}else{
				System.out.println( question.getQ_type() );
			}
		}
		/////////////////////////////////
		
		if( question.getQ_type() == QUESTION_TYPE.RADIO )
		{
		// adding radio group per question
		answersPerQuestionWrapper.addView(radioGroupPerQuestion);
		}
		
		// add single question to singleQuestionWrapper
		singleQuestionWrapper.addView(textViewPerQuestion);		
		
		// add single question answers wrapper to singleQuestionWrapper
		singleQuestionWrapper.addView(answersPerQuestionWrapper);		
		
		// add single question wrapper
		allQuestionsWrapper.addView(singleQuestionWrapper);
		
		}
		
	}
	
	private void prepareSurveyData() {
		LinearLayout allQuestionsWrapper = ((LinearLayout) findViewById(R.id.act_detail_survey_ll_all_question_wrapper));
		
		allQuestionsWrapper.getChildCount();
		
		answerData = new ArrayList<AnswerData>();
		
		for(int q=0 ; q < allQuestionsWrapper.getChildCount() ; q++){
			
			LinearLayout singleQuestionWrapper = (LinearLayout) allQuestionsWrapper.getChildAt(q);
			
			TextView textViewPerQuestion = (TextView) singleQuestionWrapper.getChildAt(0);
			
			Question foundQuestion = getQuestionByStringRepresentation( textViewPerQuestion.getText().toString() );
			
			if( foundQuestion == null)
				continue;
			
			LinearLayout answersPerQuestionWrapper = (LinearLayout) singleQuestionWrapper.getChildAt(1);
			
			for( int a=0 ; a < answersPerQuestionWrapper.getChildCount() ; a++ ){

				String answer = null;
				View someChild = answersPerQuestionWrapper.getChildAt(a);
					
				if( answersPerQuestionWrapper.getChildAt(a) instanceof RadioGroup ){
					RadioGroup rg = (RadioGroup) answersPerQuestionWrapper.getChildAt(a);
					for( int rb = 0; rb < rg.getChildCount() ; rb++){
						RadioButton rButton = (RadioButton) rg.getChildAt(rb);
						if(rButton.isSelected()){
							answer = rButton.getText().toString();
							answerData.add( new AnswerData(this.detailSurvey.getS_id(), getAnsweByStringRepresentation(answer).getA_id(), null) );
							break;
						}
					}
				}else if ( someChild instanceof CheckBox ){
					answer = ((CheckBox) someChild).getText().toString();
					answerData.add( new AnswerData(this.detailSurvey.getS_id(), getAnsweByStringRepresentation(answer).getA_id(), null) );
					
				}else if( someChild instanceof EditText){
					EditText etRaw = ((EditText) someChild );
					ViewParent vp = etRaw.getParent();
					LinearLayout answersPerQuestionWrapperXXX = (LinearLayout) vp;
					LinearLayout singleQuestionWrapperXXX = (LinearLayout) answersPerQuestionWrapperXXX.getParent();
					
					for( int ch = 0 ; ch <  singleQuestionWrapperXXX.getChildCount() ; ch++ ){
						if( singleQuestionWrapperXXX.getChildAt(ch) instanceof  TextView ){
							TextView questionForRawAnswer = (TextView) singleQuestionWrapperXXX.getChildAt(ch);
							Question qRaw = getQuestionByStringRepresentation(questionForRawAnswer.getText().toString());
							String rawAnswer = etRaw.getText().toString();
//							System.out.println("Answer="+answer);
//							System.out.println("SurveyID="+this.detailSurvey.getS_id());
//							System.out.println(answerData.toString());
							answerData.add( new AnswerData(this.detailSurvey.getS_id(), qRaw.getQ_id(), rawAnswer) );							
						}
					}
				}
			}
		}
		
		System.out.println("---------------------------------------------------------------------------------------");
		System.out.println(answerData.toString());
		
	}	
	
	private Question getQuestionByStringRepresentation(String questionString){
		Question result = null;
		
		Iterator<Question> qIterator = this.questionsArray.iterator();
		
		while( qIterator.hasNext() ){
			result = qIterator.next();
			if( result.getQ_question().equalsIgnoreCase(questionString) )
				return result;
		}
		
		return null;
	}
	
	private Answer getAnsweByStringRepresentation(String answerString){
		Answer result = null;
		
		Iterator<Question> qIterator = this.questionsArray.iterator();
		
		while( qIterator.hasNext() ){
			Question question = qIterator.next();
			for ( Answer a : question.getAnswers() ) {
				if( a.getA_answer().equalsIgnoreCase(answerString) ){
					result = a;
					return result;
				}
			}
		}
		
		return null;
	}	

	@Override
	public void onPreExecute() {
		pDialog = new ProgressDialog(ADetailSurveyView.this);
		pDialog.setMessage("...loading survey questions and answers...");
		pDialog.setIndeterminate(false);
	    pDialog.setCancelable(true);
	    pDialog.show();		
	}

}
