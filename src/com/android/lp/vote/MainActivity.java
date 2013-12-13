package com.android.lp.vote;



import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	Button btn_others;
	Dialog custom;
	EditText et_hash_code;
	EditText et_keywords;
	Button btn_showall;
	Button btn_cancel;
	Button btn_search_by_hc;
	Button btn_search_by_keyword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final Button showActivityMySurveysButton = (Button) findViewById(R.id.btn_survey);
		showActivityMySurveysButton
				.setOnClickListener(new View.OnClickListener() {

					public void onClick(View view) {
						startActivityMySurveys();
					}
				});
			

		final Button ShowActivityInfoButton = (Button) findViewById(R.id.btn_info);
		ShowActivityInfoButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				startActivityInfo();
			}
		});

		Button ShowActivityAboutButton = (Button) findViewById(R.id.btn_about);
		ShowActivityAboutButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				startActivityAbout();
			}
		});
		
		btn_others = (Button)findViewById(R.id.btn_others);
        btn_others.setOnClickListener(new View.OnClickListener() {
           
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                custom = new Dialog(MainActivity.this);
                custom.setContentView(R.layout.dialog);
                et_hash_code = (EditText)custom.findViewById(R.id.et_hash_code);
                btn_search_by_hc = (Button)custom.findViewById(R.id.btn_search_by_hc);
                et_keywords = (EditText)custom.findViewById(R.id.et_keywords);
                btn_search_by_keyword = (Button)custom.findViewById(R.id.btn_search_by_keyword);
                btn_showall = (Button)custom.findViewById(R.id.btn_showall);
                btn_cancel = (Button)custom.findViewById(R.id.btn_cancel);
          
                custom.setTitle("Search by Hash Code or Keywords!");
                btn_showall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    	startActivityShowAllSurveys();
                    }
                });
                
                btn_search_by_hc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    	startActivityShowHashCodeSurveys();
                    }
                });
                
                btn_search_by_keyword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    	startActivityShowKeywordSurveys();
                    }
                });
                
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO Auto-generated method stub
                        custom.dismiss();
                    }
                });
                custom.show();
            }
        });
	}

	private void startActivityInfo() {
		Intent startTwo = new Intent(this, ActivityInfo.class);
		startActivity(startTwo);
	}

	private void startActivityAbout() {
		Intent startTwo = new Intent(this, ActivityAbout.class);
		startActivity(startTwo);
	}

	private void startActivityMySurveys() {
		Intent startMySurveysActivity = new Intent(this, ActivityMySurvey.class);
		startActivity(startMySurveysActivity);
	}
	
	private void startActivityShowAllSurveys() {
		Intent startTwo = new Intent(this, ShowAllSurveys.class);
		startActivity(startTwo);
	}
    
    private void startActivityShowHashCodeSurveys() {
    	Intent startTwo = new Intent(this, ShowByHashCode.class);
    	startActivity(startTwo);
    }
    
    private void startActivityShowKeywordSurveys() {
    	Intent startTwo = new Intent(this, ShowByKeyword.class);
    	startActivity(startTwo);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
