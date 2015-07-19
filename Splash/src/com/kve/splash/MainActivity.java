package com.kve.splash;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {

	MediaPlayer logoSound;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		logoSound = MediaPlayer.create(MainActivity.this, R.raw.wave);
		logoSound.start();
		
		Thread logoTimer = new Thread(){
				public void run(){
					try {
						sleep(100);
						Intent menuIntent = new Intent("com.kve.splash.SPLASHMENU");
						startActivity(menuIntent);
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					finally{
						finish();
					}
				}
			};
			logoTimer.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		logoSound.release();
	}

}
