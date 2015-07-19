package com.kve.series40;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class S40Menu extends Activity implements OnClickListener {

	String activities[] = {"ST", "SETTINGS", "INTERNALSTORAGE", "READING", "SAVETOSD"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.s40menu);
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int btnId = v.getId();
		
		for (int aCounter = 0 ; aCounter < activities.length ; aCounter++)
		{
			if (getResources().getIdentifier("b" + aCounter, "id", "com.kve.series40") == btnId)
			{
				Intent menuIntent = new Intent("com.kve.series40." + activities[aCounter]);
				startActivity(menuIntent);
				break;
			}
		}
	}

}
