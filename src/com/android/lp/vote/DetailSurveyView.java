package com.android.lp.vote;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;

public class DetailSurveyView extends Activity {

	boolean isButton=true;
	
	 SparseArray<Group> groups = new SparseArray<Group>();

	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.detail_survey_view);
	    
	    Button myphotosBtn = (Button)findViewById(R.id.btn_show_questions);
	    
	    myphotosBtn.setOnClickListener(new OnClickListener() {

           @Override
           public void onClick(View v) {


           
			if(isButton){
                     //  arrowDown.setImageResource(R.drawable.arrow_up);
                      findViewById(R.id.ListView).setVisibility(View.VISIBLE);
                      isButton=false;
           }else{
                       // arrowDown.setImageResource(R.drawable.down);
                      findViewById(R.id.ListView).setVisibility(View.GONE);
                      isButton=true;
                }
           }
       });
	    
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

