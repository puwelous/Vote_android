package com.android.lp.vote;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Properties;
import java.util.regex.PatternSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.lp.communication.IReactor;
import com.android.lp.communication.JSONSendTask;
import com.android.lp.helpers.AssetsPropertyReader;
import com.android.lp.vote.models.Survey;
import com.android.lp.vote.models.Survey.SURVEY_TYPE;
import com.google.gson.Gson;

public class ActivityNewSurvey extends FragmentActivity implements IReactor {

	
	public static final int GET_CODE = 1;
	// private ExchangeDataFormatter exchangeDataFormatter = new
	// ExchangeDataFormatter();
	private AssetsPropertyReader assetsPropertyReader = null;

	private String url_survey_create = null;
	
	private ProgressDialog pDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_new_survey);

		// initializing property reader
		Properties properties;
		try {
			assetsPropertyReader = new AssetsPropertyReader(this);
			properties = assetsPropertyReader
					.getProperties("RESTCallURLs.properties");
			url_survey_create = properties.getProperty("survey_create");

		} catch (IOException e) {
			Log.e(getClass().getName(), e.getLocalizedMessage());
			Toast.makeText(getApplicationContext(), e.getLocalizedMessage(),
					Toast.LENGTH_LONG).show();
		}

		((Button) findViewById(R.id.btn_create))
				.setOnClickListener(new View.OnClickListener() {

					public void onClick(View view) {

						Survey newSurvey = createSurveyFromInput();
						
						if( !newSurvey.validate() ){
							Toast.makeText(getApplicationContext(), "Wrong input!",
									Toast.LENGTH_LONG).show();
							return;
						}
						
						
						accessWebServiceCreateSurvey(newSurvey);
					}
				});
	}
	
	private Survey createSurveyFromInput(){
		
		// TODO: id of user
		Long newSurveyCreator = new Long(2);
		String newSurveyTitle = ((EditText) findViewById(R.id.et_new_survey))
				.getText().toString();

		RadioGroup rg_surveyType = (RadioGroup) findViewById(R.id.rg_survey_type);
		RadioButton radioButtonSelected = (RadioButton) this
				.findViewById(rg_surveyType.getCheckedRadioButtonId());

		Survey.SURVEY_TYPE newSurveyType = (radioButtonSelected == (RadioButton) findViewById(R.id.rb_surv_type_global) ? Survey.SURVEY_TYPE.GLOBAL
				: Survey.SURVEY_TYPE.LOCAL);
		String newSurveyStartTime = ((EditText) findViewById(R.id.et_start_date))
				.getText().toString()
				+ " "
				+ ((EditText) findViewById(R.id.et_start_time)).getText()
						.toString();
		String newSurveyEndTime = ((EditText) findViewById(R.id.et_end_date))
				.getText().toString()
				+ " "
				+ ((EditText) findViewById(R.id.et_end_time)).getText()
						.toString();
		String newSurveyHashOrUrl = "s_hash_or_url";

		return new Survey(newSurveyCreator, newSurveyTitle,
				newSurveyType, newSurveyStartTime, newSurveyEndTime,
				newSurveyHashOrUrl);		
	}

	private void accessWebServiceCreateSurvey(Survey newSurvey) {

		String[] keyWords;
		JSONObject clientJSONMessage = new JSONObject();

		//jsonNewSurveyAsString = new Gson().toJson(newSurvey);
		
		JSONObject newSurveyAsJSONObject = new JSONObject();
		

		try {

			newSurveyAsJSONObject.put("s_creator", newSurvey.getS_creator());
			newSurveyAsJSONObject.put("s_title", newSurvey.getS_title());
			newSurveyAsJSONObject.put("s_type", newSurvey.getS_type().name().toUpperCase());
			newSurveyAsJSONObject.put("s_start_time", newSurvey.getS_start_time());
			newSurveyAsJSONObject.put("s_end_time", newSurvey.getS_end_time());
			newSurveyAsJSONObject.put("s_hash_or_url", newSurvey.getS_hash_or_url());
			
			///
	        String jsonEmp = new Gson().toJson(newSurvey);
	        System.out.print(jsonEmp);
			///
			
			
			System.out.println("newSurveyAsJSONObject"  + newSurveyAsJSONObject);
			
			//clientJSONMessage = clientJSONMessage.put("v_survey", newSurveyAsJSONObject);
			clientJSONMessage = clientJSONMessage.put("v_survey", jsonEmp);

			String keyWordsFullString = ((EditText) findViewById(R.id.main_dialog_et_keywords))
					.getText().toString();

			//try to parse keywords
			keyWords = keyWordsFullString.split("\\s+");
			
			if( keyWords.length >= 0 ){
				JSONArray keyWordsJSONArray = new JSONArray();
				
				for (int i = 0; i < keyWords.length; i++) {
					keyWordsJSONArray.put(keyWords[i]);
				}
				
				System.out.println("Putting keyword:" + Arrays.deepToString(keyWords));
				
				clientJSONMessage = clientJSONMessage.put("v_keywords", keyWordsJSONArray);
			}
			
			JSONSendTask task = new JSONSendTask(this);

			//System.out.println("by new Gson() " + new Gson().toJson(clientJSONMessage));
			System.out.println("by toString() " + clientJSONMessage.toString() );
			
			//task.execute(new String[] { url_survey_create, jsonNewSurveyAsString });
			task.execute(new String[] { url_survey_create, clientJSONMessage.toString() } );

		} catch (PatternSyntaxException ex) {
			// ignore keywords
			ex.printStackTrace();
			Toast.makeText(getApplicationContext(),
					"PatternSyntaxException " + ex.getMessage(), Toast.LENGTH_LONG).show();			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(getApplicationContext(),
					"JSONException " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	public void selectStartDate(View view) {
		DialogFragment newFragment = new SelectDateFragment(R.id.et_start_date);
		newFragment.show(getSupportFragmentManager(), "DatePicker");
	}

	public void selectEndDate(View view) {
		DialogFragment newFragment = new SelectDateFragment(R.id.et_end_date);
		newFragment.show(getSupportFragmentManager(), "DatePicker");
	}

	public void selectStartTime(View view) {
		DialogFragment newFragment = new SelectTimeFragment(R.id.et_start_time);
		newFragment.show(getSupportFragmentManager(), "TimePicker");
	}

	public void selectEndTime(View view) {
		DialogFragment newFragment = new SelectTimeFragment(R.id.et_end_time);
		newFragment.show(getSupportFragmentManager(), "TimePicker");
	}

	public void populateSetDate(int datePickerId, int year, int month, int day) {
		EditText editText_date = (EditText) findViewById(datePickerId);
		editText_date.setText(year + "-" + month + "-" + day);
	}

	public void populateSetTime(int timePickerId, int hour, int minute) {
		EditText editText_time = (EditText) findViewById(timePickerId);
		editText_time.setText(hour + ":" + minute);
	}

	public class SelectDateFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		private final int datePickerId;

		public SelectDateFragment(int datePickerId) {
			super();
			this.datePickerId = datePickerId;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar calendar = Calendar.getInstance();
			int yy = calendar.get(Calendar.YEAR);
			int mm = calendar.get(Calendar.MONTH);
			int dd = calendar.get(Calendar.DAY_OF_MONTH);
			return new DatePickerDialog(getActivity(), this, yy, mm, dd);
		}

		public void onDateSet(DatePicker view, int yy, int mm, int dd) {
			populateSetDate(this.datePickerId, yy, mm + 1, dd);
		}
	}

	public class SelectTimeFragment extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {

		private final int timePickerId;

		public SelectTimeFragment(int timePickerId) {
			super();
			this.timePickerId = timePickerId;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar calendar = Calendar.getInstance();
			int hh = calendar.get(Calendar.HOUR_OF_DAY);
			int mmm = calendar.get(Calendar.MINUTE);
			return new TimePickerDialog(getActivity(), this, hh, mmm,
					DateFormat.is24HourFormat(getActivity()));
		}

		public void onTimeSet(TimePicker view, int hh, int mmm) {
			populateSetTime(this.timePickerId, hh, mmm);
		}
	}

	@Override
	public void onPostExecute(String result) {

		pDialog.dismiss();
		
		// TODO: Handle result
		JSONObject message;
		Long newSurveyId = null;

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
			newSurveyId = Long.parseLong(dataObject.getString("s_id"));

			if (newSurveyId == null ) {
				throw new JSONException("New survey ID not received!");
			}

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

		// put survey ID into shared preferences
		Intent intent_ActivityQuestions = new Intent(this,
				ActivityQuestions.class);
		intent_ActivityQuestions.putExtra("NEW_SURVEY_ID", newSurveyId);

		startActivityForResult(intent_ActivityQuestions, GET_CODE);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
				
		if (requestCode == GET_CODE && resultCode == RESULT_OK) {
			//String activityResult = data.getStringExtra("COLOR");
			//tv.setText(activityResult);
			Toast.makeText(getApplicationContext(), "SerializableData: SoFarNothing", //+ data.getSerializableExtra(name),
					Toast.LENGTH_LONG).show();				
		}
	}

	@Override
	public void onPreExecute() {
		pDialog = new ProgressDialog(ActivityNewSurvey.this);
		pDialog.setMessage("...creating new survey...");
		pDialog.setIndeterminate(false);
	    pDialog.setCancelable(true);
	    pDialog.show();
	}	
}