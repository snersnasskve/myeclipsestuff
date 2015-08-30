package com.kve.distcalc;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements OnClickListener{

	private Button btnMilesToKm;
	private Button btnKmToMiles;
	private EditText etKilometres;
	private EditText etMiles;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) 
		{
			etKilometres = (EditText) 	findViewById(R.id.etKilometres);
			etMiles = 		(EditText)	findViewById(R.id.etMiles);
			btnMilesToKm = 	(Button) 	findViewById(R.id.btnMilesToKm);
			btnKmToMiles = 	(Button) 	findViewById(R.id.btnKmToMiles);

			btnMilesToKm.setOnClickListener(this);
			btnKmToMiles.setOnClickListener(this);




		}
		//	else it should just figure itself out
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	@Override
	public void onClick(View v) {
		Double miles;
		Double km;
		switch (v.getId()) {
		case R.id.btnMilesToKm:
			miles = Double.parseDouble(etMiles.getText().toString());
			km = (miles * 1.609344 );
			etKilometres.setText("" + Math.round(km * 100.0)/100.0);
		case R.id.btnKmToMiles:
			km = Double.parseDouble(etKilometres.getText().toString());
			miles = (km / 1.609344);
			etMiles.setText("" + Math.round(miles * 100.0) / 100.0);

			break;
		default:
			break;
		}		
	}

}
