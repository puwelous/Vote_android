package com.android.lp.vote;

import com.android.lp.vote.models.Survey;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class DetailSurveyView extends Activity {

	boolean isButton = true;

	SparseArray<Group> groups = new SparseArray<Group>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_survey_view);

		Button myphotosBtn = (Button) findViewById(R.id.btn_show_questions);

		myphotosBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (isButton) {
					// arrowDown.setImageResource(R.drawable.arrow_up);
					findViewById(R.id.ListView).setVisibility(View.VISIBLE);
					isButton = false;
				} else {
					// arrowDown.setImageResource(R.drawable.down);
					findViewById(R.id.ListView).setVisibility(View.GONE);
					isButton = true;
				}
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

		Survey detailSurvey = (Survey) thisIntent
				.getSerializableExtra("SINGLE_SURVEY");

		// ((TextView) findViewById(R.id.tv_real_creator_id))
		// .setText(String.valueOf(thisIntent.getLongExtra(
		// "SURVEY_CREATOR", new Long(0))));
		// ((TextView) findViewById(R.id.tv_surveys_name)).setText(thisIntent
		// .getStringExtra("SURVEY_TITLE"));
		// ((TextView) findViewById(R.id.tv_real_start_date)).setText(thisIntent
		// .getStringExtra("SURVEY_START_TIME"));
		// ((TextView) findViewById(R.id.tv_real_end_date)).setText(thisIntent
		// .getStringExtra("SURVEY_END_TIME"));
		// ((TextView) findViewById(R.id.tv_real_type)).setText(thisIntent
		// .getStringExtra("SURVEY_TYPE"));
		// ((TextView) findViewById(R.id.tv_real_hash_code)).setText(thisIntent
		// .getStringExtra("SURVEY_HASH_OR_URL"));

		((TextView) findViewById(R.id.tv_real_creator_id)).setText(String
				.valueOf(detailSurvey.getS_creator()));

		((TextView) findViewById(R.id.tv_surveys_name)).setText(detailSurvey.getS_title());
		((TextView) findViewById(R.id.tv_real_type)).setText(detailSurvey.getS_type().name());
		((TextView) findViewById(R.id.tv_real_hash_code)).setText(detailSurvey.getS_hash_or_url());

		String timestamp_start[] = detailSurvey.getS_start_time().split("\\s+");
		
		((TextView) findViewById(R.id.tv_real_start_date)).setText( timestamp_start[0] );
		((TextView) findViewById(R.id.tv_real_start_time)).setText( timestamp_start[1] );
		
		String timestamp_end[] = detailSurvey.getS_end_time().split("\\s+");
		
		((TextView) findViewById(R.id.tv_real_end_date)).setText( timestamp_end[0] );
		((TextView) findViewById(R.id.tv_real_end_time)).setText( timestamp_end[1] );	
		
		createData();
		ExpandableListView listView = (ExpandableListView) findViewById(R.id.ListView);
		MyExpandableListAdapter adapter = new MyExpandableListAdapter(this,
				groups);
		listView.setAdapter(adapter);
	}

	public void createData() {
		for (int j = 0; j < 5; j++) {
			Group group = new Group("Test " + j);
			for (int i = 0; i < 5; i++) {
				group.children.add("Sub Item" + i);
			}
			groups.append(j, group);
		}
	}

}
