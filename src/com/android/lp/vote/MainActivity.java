package com.android.lp.vote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button ShowActivityInfoButton = (Button) findViewById
				(R.id.btn_info);
		ShowActivityInfoButton.setOnClickListener(new View.OnClickListener() {
				
			public void onClick(View view) {
				// TODO Auto-generated method stub
				startActivityInfo();
			}
		});
		
		Button ShowActivityAboutButton = (Button) findViewById
				(R.id.btn_about);
		ShowActivityAboutButton.setOnClickListener(new View.OnClickListener() {
				
			public void onClick(View view) {
				// TODO Auto-generated method stub
				startActivityAbout();
			}
		});
		

		Button showActivityMySurveysButton = (Button) findViewById
				(R.id.btn_survey);
		showActivityMySurveysButton.setOnClickListener(new View.OnClickListener() {
				
			public void onClick(View view) {
				startActivityMySurveys();
			}
		});
	}
	
	private void startActivityInfo(){
		Intent startTwo= new Intent(this, ActivityInfo.class);
		startActivity(startTwo);
	}
	
	private void startActivityAbout(){
		Intent startTwo= new Intent(this, ActivityAbout.class);
		startActivity(startTwo);
	}
	
	private void startActivityMySurveys(){
		Intent startMySurveysActivity = new Intent(this, ActivityMySurvey.class);
		startActivity(startMySurveysActivity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
