package com.android.lp.vote;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Stats extends Activity implements OnClickListener{

	Button  barGraph;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);
		
        barGraph = (Button) findViewById(R.id.BarGraph);
        barGraph.setOnClickListener(this);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stats, menu);
		return true;
	}

    @Override
    public void onClick(View v) {
        
        switch(v.getId()){
            
              case R.id.BarGraph:
                  BarGraph bar = new BarGraph();
                  Intent barIntent = bar.getIntent(this);
                  startActivity(barIntent);
                  break;
        }
        
    }

}
