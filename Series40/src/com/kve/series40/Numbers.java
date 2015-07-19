package com.kve.series40;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Numbers extends Activity implements OnClickListener {

	EditText number;
	Button sendInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.numbers);
		
		number = (EditText) findViewById(R.id.etNumber);
		sendInfo = (Button) findViewById(R.id.btnEnterNum);
		sendInfo.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String dataEntered = number.getText().toString();
		Intent receivedIntent = getIntent();
		String dataKey = receivedIntent.getStringExtra("number");
		
		//receivedIntent.putExtra(dataKey, dataEntered);
		if (dataKey.contentEquals("width"))
		{
			receivedIntent.putExtra("widthInfo", dataEntered);
			setResult(RESULT_OK, receivedIntent);
			finish();
		}
		else if (dataKey.contentEquals("height"))
		{
			receivedIntent.putExtra("heightInfo", dataEntered);
			setResult(RESULT_OK, receivedIntent);
			finish();
		}
	}

}
