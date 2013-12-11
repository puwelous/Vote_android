package com.android.lp.vote;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ActivityQuestions extends Activity {

	private Long survey_id;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_questions);
	
		// retrieve new survey ID from extras
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    survey_id = extras.getLong("NEW_SURVEY_ID");
		}
		
		Toast.makeText(getApplicationContext(), "Survey ID: " + survey_id,
				Toast.LENGTH_LONG).show();
		
	Button ShowActivityAddQuestButton = (Button) findViewById
			(R.id.btn_AddQuest);
	ShowActivityAddQuestButton.setOnClickListener(new View.OnClickListener() {
			
		public void onClick(View view) {
			// TODO Auto-generated method stub
			startActivityAddQuest();
		}
	});


	Button ShowActivityMySurveyButton = (Button) findViewById
			(R.id.btn_finish);
	ShowActivityMySurveyButton.setOnClickListener(new View.OnClickListener() {
			
		public void onClick(View view) {
			// TODO Auto-generated method stub
			startActivityMySurvey();
		}
	});
}

private void startActivityAddQuest(){
	Intent startTwo= new Intent(this, ActivityAddQuestion.class);
	startActivity(startTwo);
}

private void startActivityMySurvey(){
	Intent startTwo= new Intent(this, ActivityMySurvey.class);
	startActivity(startTwo);
}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_new_questions, menu);
		return true;
	}

}
