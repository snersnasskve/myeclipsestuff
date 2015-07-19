package com.kve.splash;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class Tutorial1 extends Activity implements OnCheckedChangeListener{

	TextView textOut;
	EditText textIn;
	RadioGroup gravityG, styleG;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.tutorial1);
		
		textOut 	= 	(TextView) 		findViewById(R.id.tvChange);
		textIn 		=	(EditText) 		findViewById(R.id.aboutText);
		gravityG 	= 	(RadioGroup) 	findViewById(R.id.rgGravity);
		styleG 		= 	(RadioGroup) 	findViewById(R.id.rgStyle);
		Button gen 	= 	(Button) 		findViewById(R.id.btnGenerate);
		
		gen.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				textOut.setText(textIn.getText());
			}
		});
		
		//	Haha caught us out a right one
		gravityG.setOnCheckedChangeListener(this);
		styleG.setOnCheckedChangeListener(this);
	}


	@Override
	public void onCheckedChanged(RadioGroup radioGroup, int radioCheckedId) {
		// TODO Auto-generated method stub
		Log.i("karen", "look");
		switch (radioCheckedId) {
		case R.id.rbLeft:
			textOut.setGravity(Gravity.LEFT);
			break;
		case R.id.rbCentre:
			textOut.setGravity(Gravity.CENTER);
			break;
		case R.id.rbRight:
			textOut.setGravity(Gravity.RIGHT);
			break;			
		case R.id.rbNormal:
			textOut.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
			break;
		case R.id.rbBold:
			textOut.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
			break;
		case R.id.rbItalic:
			textOut.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
			break;			
		default:
			Log.i("karen", "not 0");

			break;
		}

	}

	

}
