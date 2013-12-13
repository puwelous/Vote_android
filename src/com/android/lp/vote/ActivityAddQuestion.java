package com.android.lp.vote;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.lp.vote.models.Answer;
import com.android.lp.vote.models.Question;

public class ActivityAddQuestion extends Activity {

	private Long survey_id = null;
	private List<Answer> answersPerQuestion = new ArrayList<Answer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_add_question);

		// retrieve new survey ID from extras
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			survey_id = extras.getLong("NEW_SURVEY_ID");
			Toast.makeText(getApplicationContext(), "Survey ID: " + survey_id,
					Toast.LENGTH_LONG).show();
		}

		Button ShowActivityDoneQuestButton = (Button) findViewById(R.id.btn_done);
		ShowActivityDoneQuestButton
				.setOnClickListener(new View.OnClickListener() {

					public void onClick(View view) {

						startActivityDoneQuest();
					}
				});

		final LinearLayout answersWrapperLayout = (LinearLayout) findViewById(R.id.layout_answers_wrapper);
		final Button bn = (Button) findViewById(R.id.btn_add_answer);
		bn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				final LinearLayout linearLayoutPerAnswer = new LinearLayout(v
						.getContext());

				linearLayoutPerAnswer.setOrientation(LinearLayout.VERTICAL);

				linearLayoutPerAnswer.addView(createTextView(v.getContext()));

				linearLayoutPerAnswer.addView(createEditText(v.getContext()));

				answersWrapperLayout.addView(linearLayoutPerAnswer);
			}
		});

		/*
		 * =======
		 * 
		 * 
		 * Button ShowActivityDoneQuestButton = (Button) findViewById
		 * (R.id.btn_done); ShowActivityDoneQuestButton.setOnClickListener(new
		 * View.OnClickListener() {
		 * 
		 * public void onClick(View view) { // TODO Auto-generated method stub
		 * startActivityDoneQuest(); } });
		 * 
		 * final LinearLayout layout = (LinearLayout)
		 * findViewById(R.id.linlayout); final Button bn = (Button)
		 * findViewById(R.id.btn_add_answer); bn.setOnClickListener(new
		 * OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { EditText etv1 = new
		 * EditText(v.getContext()); etv1.setText("Answer");
		 * layout.addView(etv1); } });
		 * 
		 * >>>>>>> f5f00d85c0c11c2b32bed6e226973101c4cc3485
		 */
	}

	private void startActivityDoneQuest() {
		Intent intent = new Intent();

		/* creating question object from user input */
		String question_content = ((android.widget.EditText) findViewById(R.id.et_question_definition))
				.getText().toString();

		Spinner questionTypeSpinner = (android.widget.Spinner) findViewById(R.id.sp_radio_checkbox_raw);
		String selItemStringRepr = (String) questionTypeSpinner
				.getItemAtPosition(questionTypeSpinner
						.getSelectedItemPosition());	
		
		Question.QUESTION_TYPE newQuestionType = (selItemStringRepr
				.equalsIgnoreCase("Single choice") ? Question.QUESTION_TYPE.RADIO
				: (selItemStringRepr
						.equalsIgnoreCase("Multiple choice") ? Question.QUESTION_TYPE.CHECKBOX
						: Question.QUESTION_TYPE.RAW));

		Question newQuestion = new Question( Long.valueOf(-1), survey_id,
				newQuestionType, question_content);

		/* creating answers per this question */
		final LinearLayout answersWrapperLayout = (LinearLayout) findViewById(R.id.layout_answers_wrapper);
		final int answersCount = answersWrapperLayout.getChildCount();

		if (answersCount > 0) {
			ArrayList<Answer> answersPerQuestion =  new ArrayList<Answer>();

			// insert answers into array
			for (int i = 0; i < answersCount; i++) {
				try {
					Answer singleAnswer = null;

					final LinearLayout linearLayoutPerAnswer = (LinearLayout) answersWrapperLayout
							.getChildAt(i);
					if (linearLayoutPerAnswer.getChildCount() < 1) {
						continue;
					}

					String answer = ((EditText) linearLayoutPerAnswer
							.getChildAt(1)).getText().toString();

					singleAnswer = new Answer( Long.valueOf(-1), newQuestion.getQ_id(), answer);

					answersPerQuestion.add( singleAnswer );
				} catch (Exception e) {
					// ignore
					e.printStackTrace();
				}
			}
			
			// set parsed answers
			newQuestion.setAnswers(answersPerQuestion);
			
			Toast.makeText(getApplicationContext(), "Size of list: " + answersPerQuestion.size(),
					Toast.LENGTH_LONG).show();
			
		
			System.out.println( newQuestion.toFullString()  );
			
			// put question into extras	
			intent.putExtra("NEW_QUESTION", (Serializable) newQuestion);

			setResult(RESULT_OK, intent);
			finish();			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_question, menu);
		return true;
	}

	private EditText createEditText(Context context) {

		// create instance
		EditText editTextPerNewSingleAnswer = new EditText(context);

		// set characteristics
		// android:layout_width="fill_parent"
		// android:layout_height="0dp"
		// android:layout_weight="1"
		editTextPerNewSingleAnswer.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
		editTextPerNewSingleAnswer.setGravity(Gravity.CENTER);
		editTextPerNewSingleAnswer.setHint("Possible answer");

		return editTextPerNewSingleAnswer;
	}

	private TextView createTextView(Context context) {

		// create instance
		TextView textViewPerNewSingleAnswer = new TextView(context);

		// set characteristics
		textViewPerNewSingleAnswer.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
		textViewPerNewSingleAnswer.setGravity(Gravity.CENTER);
		textViewPerNewSingleAnswer.setTextAppearance(context,
				android.R.style.TextAppearance_Medium);
		textViewPerNewSingleAnswer.setText("Answer X:");

		return textViewPerNewSingleAnswer;
	}
}
