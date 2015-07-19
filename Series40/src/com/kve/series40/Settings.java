package com.kve.series40;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Settings extends Activity implements OnClickListener {

	CheckBox cbDisplay;
	EditText etName;
	Button btnSave;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		cbDisplay	= (CheckBox) findViewById(R.id.cbDispName);
		etName		= (EditText) findViewById(R.id.etName);
		btnSave		= (Button) 	 findViewById(R.id.btnSave);
		
		btnSave.setOnClickListener(this);
		loadPrefs();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		loadPrefs();
	}


	private void loadPrefs()	{
		SharedPreferences sp =
				PreferenceManager.getDefaultSharedPreferences(this);
	String n = sp.getString("NAME", "not found");
	Boolean c = sp.getBoolean("CHECKBOX", false);
	etName.setText(n);
	cbDisplay.setChecked(c);
	
	}
	
	private void savePrefs(String key, boolean value)	{
		SharedPreferences sp =
				PreferenceManager.getDefaultSharedPreferences(this);
		Editor ed = sp.edit();
		ed.putBoolean(key, value);
		ed.commit();
	}

	private void savePrefs(String key, String value)	{
		SharedPreferences sp =
				PreferenceManager.getDefaultSharedPreferences(this);
		Editor ed = sp.edit();	
		ed.putString(key, value);
		ed.commit();
	}

	@Override
	public void onClick(View arg0) {
		savePrefs("CHECKBOX", 	cbDisplay.isChecked());
		if (cbDisplay.isChecked())
		{
			savePrefs("NAME", 		etName.getText().toString());
		}
		finish();
	}

}
