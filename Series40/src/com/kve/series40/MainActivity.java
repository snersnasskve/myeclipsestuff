package com.kve.series40;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	Button width, height, calc, settings, store;
	TextView area;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		width 	= (Button)   findViewById(R.id.btnWidth);
		height 	= (Button)   findViewById(R.id.btnHeight);
		calc	= (Button)   findViewById(R.id.btnCalc);
		settings= (Button)   findViewById(R.id.btnSettings);
		store	= (Button)   findViewById(R.id.btnStorage);
		area 	= (TextView) findViewById(R.id.tvArea);
		
		width.setOnClickListener(this);
		height.setOnClickListener(this);
		calc.setOnClickListener(this);
		settings.setOnClickListener(this);
		store.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		Intent numbersIntent = new Intent(this, Numbers.class);

		switch (v.getId()) {
		case R.id.btnWidth:
			numbersIntent.putExtra("number", "width");
			startActivityForResult(numbersIntent, 1);
			break;
		case R.id.btnHeight:
			numbersIntent.putExtra("number", "height");
			startActivityForResult(numbersIntent, 1);
		break;
		case R.id.btnCalc:
			float resultArea = Float.parseFloat(width.getText().toString()) * 
					Float.parseFloat(height.getText().toString());
				area.setText("" + resultArea);
			break;
		case R.id.btnSettings:
			Intent settingsIntent = new Intent(this, Settings.class);
			startActivity(settingsIntent);
			break;
		case R.id.btnStorage:
			Intent storageIntent = new Intent(this, InternalStorage.class);
			startActivity(storageIntent);
			break;


		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (data.getExtras().containsKey("heightInfo"))
		{
			height.setText(data.getStringExtra("heightInfo"));		
		}
		else	if (data.getExtras().containsKey("widthInfo"))
		{
			width.setText(data.getStringExtra("widthInfo"));			
		}

			
		/*
		if (data.getStringExtra("number").contentEquals("height"))
		{
			area.setText(data.getStringExtra("heightInfo"));
		}
		else if (data.getStringExtra("number").contentEquals("width"))
		{
			area.setText(data.getStringExtra("widthInfo"));
		}
		*/
	}

}
