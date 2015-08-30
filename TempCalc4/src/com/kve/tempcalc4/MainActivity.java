package com.kve.tempcalc4;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity  implements OnClickListener{

	private Button btnCalculate;
	private TextView tvAnswerValue;
	private EditText etValue;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) 
		{
			tvAnswerValue = (TextView) 	findViewById(R.id.tvAnswerValue);
			etValue = 		(EditText)	findViewById(R.id.etValue);
			btnCalculate = 	(Button) 	findViewById(R.id.btnCalculate);

			btnCalculate.setOnClickListener(this);




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
		switch (v.getId()) {
		case R.id.btnCalculate:
			Double fahr = Double.parseDouble(etValue.getText().toString());
			Double cent = ((fahr - 32.0)* (5.0/9.0));
			tvAnswerValue.setText("" + Math.round(cent));

			break;
		default:
			break;
		}		
	}

}
