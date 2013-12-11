package com.android.lp.vote;

import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import com.google.gson.Gson;

public class ActivityNewSurvey extends FragmentActivity implements IReactor {

	//private ExchangeDataFormatter exchangeDataFormatter = new ExchangeDataFormatter();
	private AssetsPropertyReader assetsPropertyReader = null;

	private String url_survey_create = null;

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

						
						
						accessWebServiceCreateSurvey();
					}
				});
	}

	private void accessWebServiceCreateSurvey() {

		String jsonNewSurveyAsString = null;

		// $new_survey = array(
		// 's_creator' => '2',
		// 's_title' => 'Dynamicly created survey',
		// 's_type' => '0',
		// 's_start_time' => '2013-12-01 15:00:00',
		// 's_end_time' => '2013-12-25 15:00:00',
		// 's_hash_or_url' => '/basic/url'
		// //,'usr_last_update' = $this->post('usr_last_update')
		// );

		Long newSurveyCreator = new Long(2);
		String newSurveyTitle = ((EditText)findViewById(R.id.et_new_survey)).getText().toString();
		
		RadioGroup rg_surveyType = (RadioGroup) findViewById(R.id.rg_survey_type);
		RadioButton radioButtonSelected =  (RadioButton) this.findViewById( rg_surveyType.getCheckedRadioButtonId() );
		
		Survey.SURVEY_TYPE newSurveyType = ( radioButtonSelected  ==  (RadioButton) findViewById(R.id.rb_surv_type_global) ? Survey.SURVEY_TYPE.GLOBAL : Survey.SURVEY_TYPE.LOCAL );
		String newSurveyStartTime = ((EditText)findViewById(R.id.et_start_date)).getText().toString() + " " + ((EditText)findViewById(R.id.et_start_time)).getText().toString();
		String newSurveyEndTime = ((EditText)findViewById(R.id.et_end_date)).getText().toString() + " " + ((EditText)findViewById(R.id.et_end_time)).getText().toString();
		String newSurveyHashOrUrl = "s_hash_or_url";
		
		Survey newSurvey = new Survey(newSurveyCreator, newSurveyTitle, newSurveyType, newSurveyStartTime, newSurveyEndTime, newSurveyHashOrUrl);
		
		jsonNewSurveyAsString = new Gson().toJson(newSurvey);
		
		System.out.println("jsonNewSurveyAsString: \n" + jsonNewSurveyAsString );

		Toast.makeText(getApplicationContext(), jsonNewSurveyAsString,
				Toast.LENGTH_LONG).show();

		JSONSendTask task = new JSONSendTask(this);

		task.execute(new String[] { url_survey_create, jsonNewSurveyAsString });
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
	public void reactOnResult(String result) {
		
		// TODO: Handle result
		JSONObject message;
		Long newSurveyId = null;
		
		try {
			message = new JSONObject(result);
			JSONObject dataObject = message.getJSONObject("v_data");
			newSurveyId = Long.parseLong( dataObject.getString("s_id") );
			
			if( newSurveyId == null){
				throw new JSONException("New survey ID not received!");
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), e.getMessage(),
					Toast.LENGTH_LONG).show();
			// prevent starting new activity!
			return;
		}
		
		// put survey ID into shared preferences
		Intent intent_ActivityQuestions = new Intent(this, ActivityQuestions.class);
		intent_ActivityQuestions.putExtra("NEW_SURVEY_ID", newSurveyId );

		startActivity(intent_ActivityQuestions);
	}
	

}