package com.kve.splash;

import android.app.Activity;
import android.os.Bundle;

public class Tutorial4 extends Activity {

	DrawingTheBall ballView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ballView = new DrawingTheBall(this);
		setContentView(ballView);
	}

}
