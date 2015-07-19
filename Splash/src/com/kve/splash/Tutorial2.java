package com.kve.splash;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Tutorial2 extends ListActivity {

	String classNames[] = {"MainActivity", "SplashMenu", "Sweet", "Tutorial1"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, classNames);
		setListAdapter(arrayAdapter);
		
	}
	
	@Override
	 protected void onListItemClick (ListView lv, View v, int position, long id)  {
		 super.onListItemClick(lv, v, position, id);
		 String openClass = classNames[position];
		 
		 try {
			Class selected = Class.forName("com.kve.splash." + openClass);
			Intent selectedIntent = new Intent(this, selected);
			startActivity(selectedIntent);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 }

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}
