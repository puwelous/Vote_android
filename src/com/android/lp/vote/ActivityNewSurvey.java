package com.android.lp.vote;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class ActivityNewSurvey extends FragmentActivity {
        
        EditText mEdit;
        EditText mEdit2;
        
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_new_survey);
    
    Button ShowActivityCreateQuestionButton = (Button) findViewById
			(R.id.btn_create);
	ShowActivityCreateQuestionButton.setOnClickListener(new View.OnClickListener() {
			
		public void onClick(View view) {
			// TODO Auto-generated method stub
			startActivityCreateQuestion();
		}
	});
}

private void startActivityCreateQuestion(){
	Intent startTwo= new Intent(this, ActivityQuestions.class);
	startActivity(startTwo);
}
    
    public void selectDate(View view) {
        DialogFragment newFragment = new SelectDateFragment();
        newFragment.show(getSupportFragmentManager(), "DatePicker");
    }
    
    public void selectDate2(View view) {
        DialogFragment newFragment = new SelectTimeFragment();
        newFragment.show(getSupportFragmentManager(), "TimePicker");
    }
    
    
    public void populateSetDate(int year, int month, int day) {
            mEdit = (EditText)findViewById(R.id.editText2);
            mEdit.setText(month+"/"+day+"/"+year);
    }
    
    public void populateSetTime(int hour, int minute) {
            mEdit2 = (EditText)findViewById(R.id.editText3);
            mEdit2.setText(hour+"/"+minute);
    }
    
    
    public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                        final Calendar calendar = Calendar.getInstance();
                        int yy = calendar.get(Calendar.YEAR);
                        int mm = calendar.get(Calendar.MONTH);
                        int dd = calendar.get(Calendar.DAY_OF_MONTH);
                        return new DatePickerDialog(getActivity(), this, yy, mm, dd);
            }
            
            public void onDateSet(DatePicker view, int yy, int mm, int dd) {
                    populateSetDate(yy, mm+1, dd);
            }
    }
    
    public class SelectTimeFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                        final Calendar calendar = Calendar.getInstance();
                        int hh = calendar.get(Calendar.HOUR_OF_DAY);
                        int mmm = calendar.get(Calendar.MINUTE);
                        return new TimePickerDialog(getActivity(), this, hh, mmm, DateFormat.is24HourFormat(getActivity()));
            }
            
            public void onTimeSet(TimePicker view, int hh, int mmm) {
                    populateSetTime(hh, mmm);
            }
    }
}