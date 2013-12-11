package com.android.lp.vote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ActivityAddQuestion extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_add_question);
/*<<<<<<< HEAD*/

		Button ShowActivityDoneQuestButton = (Button) findViewById(R.id.btn_done);
		ShowActivityDoneQuestButton
				.setOnClickListener(new View.OnClickListener() {

					public void onClick(View view) {
						// TODO Auto-generated method stub
						startActivityDoneQuest();
					}
				});

		final LinearLayout layout = (LinearLayout) findViewById(R.id.linlayout);
		final Button bn = (Button) findViewById(R.id.btn_add_answer);
		bn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText etv1 = new EditText(v.getContext());
				etv1.setText("Answer");
				layout.addView(etv1);
			}
		});

/*=======
		
		
		Button ShowActivityDoneQuestButton = (Button) findViewById
				(R.id.btn_done);
		ShowActivityDoneQuestButton.setOnClickListener(new View.OnClickListener() {
				
			public void onClick(View view) {
				// TODO Auto-generated method stub
				startActivityDoneQuest();
			}
		});
		
		final LinearLayout layout = (LinearLayout) findViewById(R.id.linlayout);
	    final Button bn = (Button) findViewById(R.id.btn_add_answer);
	    bn.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	            EditText etv1 = new EditText(v.getContext());
	            etv1.setText("Answer");
	            layout.addView(etv1);
	        }
	    });
		
>>>>>>> f5f00d85c0c11c2b32bed6e226973101c4cc3485
*/
	}

	private void startActivityDoneQuest() {
		Intent startTwo = new Intent(this, ActivityQuestions.class);
		startActivity(startTwo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_question, menu);
		return true;
	}

}
