package com.android.lp.vote;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class ActivityAddQuestion extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_add_question);
		
		Button ShowActivityDoneQuestButton = (Button) findViewById
				(R.id.btn_done);
		ShowActivityDoneQuestButton.setOnClickListener(new View.OnClickListener() {
				
			public void onClick(View view) {
				// TODO Auto-generated method stub
				startActivityDoneQuest();
			}
		});
	}
	
	private void startActivityDoneQuest(){
		Intent startTwo= new Intent(this, ActivityQuestions.class);
		startActivity(startTwo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_question, menu);
		return true;
	}

}
