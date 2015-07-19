package com.kve.chorerota;

import java.util.ArrayList;
import java.util.HashMap;


import com.kve.chorerota.data.*;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class ChoreMainActivity extends ListActivity {

	private ChoreViewAdapter mAdapter;

    final static int ADD_REQUEST_CODE 	= 0;
    final static int EDIT_REQUEST_CODE 	= 1;
    
	public static DBTools 		database; 


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
             
        database = new DBTools(ChoreMainActivity.this);
        
        //	Read database for chores
        //	Add chores to list
        ListView choresListView = getListView();

       LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View footerView = inflater.inflate(R.layout.footer_view, null);  // i have open a webview on the listview footer
       footerView.setOnClickListener(new OnClickListener() {

      	 @Override
      	 public void onClick(View arg0) {
      		 	//	Add new chore activity
     		Toast.makeText(getApplicationContext(), "Add a new chore to Rota...", 
    				Toast.LENGTH_SHORT).show();

      		 Intent addIntent = new Intent(getApplication(), ChoreAdd.class);
      		 startActivityForResult(addIntent, ADD_REQUEST_CODE);

      	 }

       });

       choresListView.addFooterView(footerView);
       mAdapter = new ChoreViewAdapter(getApplicationContext());
       setListAdapter(mAdapter);

       resetChoreListView();
        //	From here we need to ....
        //	Add a new chore
        //	Edit a chore
        //	Delete a chore
        
        //	To add, we need an add button on the main page
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	// TODO Auto-generated method stub
    	super.onListItemClick(l, v, position, id);

    	ChoreRecord selectedChore = mAdapter.getItem(position);
    	Intent editIntent = new Intent(getApplication(), ChoreEdit.class);
    	editIntent.putExtra("choreId", 		selectedChore.getChoreId());
    	editIntent.putExtra("choreName", 	selectedChore.getChoreName());
    	editIntent.putExtra("freqNo", 		selectedChore.getFreqNo());
    	editIntent.putExtra("freqUnits", 	selectedChore.getFreqUnits());
    	editIntent.putExtra("baseDate", 	selectedChore.getBaseDateString());
    	editIntent.putExtra("baseTime", 	selectedChore.getBaseTimeString());
    	editIntent.putExtra("notify", 		(selectedChore.isToNotify()) ? 1 : 0);
    	editIntent.putExtra("reqDismissal", (selectedChore.isReqDismissal()) ? 1 : 0);

 		 startActivityForResult(editIntent, EDIT_REQUEST_CODE);

    }


	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_REQUEST_CODE || requestCode == EDIT_REQUEST_CODE ) {
            if (resultCode == RESULT_OK) {
            	resetChoreListView();
            }
           }
        }


    private void resetChoreListView() {
    	mAdapter.removeAllViews();	
    	ArrayList<HashMap<String, Object>> chores  = database.getChores();
    	//	loop through chores and add to adapter
    	for (HashMap<String, Object> choreValues : chores)
    	{

        		ChoreRecord choreRec = new ChoreRecord(
    				(Long) choreValues.get("choreId"), 
    				(String) choreValues.get("choreName"),
    				(String) choreValues.get("baseDate"),
    				(String) choreValues.get("baseTime"),
    				(Float ) choreValues.get("timeNo"),
    				(String) choreValues.get("timeUnit"),
    				((Integer)choreValues.get("toNotify") == 1) ? true : false,
    						((Integer)choreValues.get("reqDismissal")== 1) ? true : false);

    		mAdapter.add(choreRec);
    	}
    }

    /*
     * 		values.put("chore_name", 	(String) choreValues.get("choreName"));
		values.put("base_date", 	(String) choreValues.get("baseDate"));
		values.put("base_time", 	(String) choreValues.get("baseTime"));
		values.put("time_no", 		(Float ) choreValues.get("timeNo"));
		values.put("time_unit",		(String) choreValues.get("timeUnit"));
		values.put("to_notify", 	(Integer)choreValues.get("toNotify"));
		values.put("req_dismissal", (Integer)choreValues.get("reqDismissal"));

     */

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chore_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
