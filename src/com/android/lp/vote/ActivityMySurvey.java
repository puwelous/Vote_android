package com.android.lp.vote;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class ActivityMySurvey extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_survey);
	
		Button ShowActivityCreateSurveyButton = (Button) findViewById
				(R.id.btn_NewSurvey);
		ShowActivityCreateSurveyButton.setOnClickListener(new View.OnClickListener() {
				
			public void onClick(View view) {
				// TODO Auto-generated method stub
				startActivityCreateSurvey();
			}
		});
	}

	private void startActivityCreateSurvey(){
		Intent startTwo= new Intent(this, ActivityNewSurvey.class);
		startActivity(startTwo);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_survey, menu);
		return true;
	}

}
