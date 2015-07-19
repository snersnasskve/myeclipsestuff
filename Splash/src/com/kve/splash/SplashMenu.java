package com.kve.splash;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SplashMenu extends Activity{

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main_menu);
			
			final MediaPlayer buttonSound = MediaPlayer.create(SplashMenu.this, R.raw.button7);
			//	Setting up the button references
			Button tut1btn = (Button)findViewById(R.id.tutorial1);
			Button tut2btn = (Button)findViewById(R.id.tutorial2);
			Button tut3btn = (Button)findViewById(R.id.tutorial3);
			Button tut4btn = (Button)findViewById(R.id.tutorial4);
			Button tut5btn = (Button)findViewById(R.id.tutorial5);
			Button tut6btn = (Button)findViewById(R.id.tutorial6);
			
			tut1btn.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					buttonSound.start();
					
					Intent tut1Intent = new Intent("com.kve.splash.TUTORIAL1");
					startActivity(tut1Intent);
				}
			
			});
			
			tut2btn.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					buttonSound.start();
					
					Intent tut2Intent = new Intent("com.kve.splash.TUTORIAL2");
					startActivity(tut2Intent);
				}
			
			});
			tut3btn.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					buttonSound.start();
					
					Intent tut3Intent = new Intent("com.kve.splash.TUTORIAL3");
					startActivity(tut3Intent);
				}
			
			});
			
			tut4btn.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					buttonSound.start();
					
					Intent tut4Intent = new Intent("com.kve.splash.TUTORIAL4");
					startActivity(tut4Intent);
				}
			
			});
	
			tut5btn.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					buttonSound.start();
					
					Intent tut5Intent = new Intent("com.kve.splash.SURFACEVIEWEXAMPLE");
					startActivity(tut5Intent);
				}
			
			});
			tut6btn.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					buttonSound.start();
					
					Intent tut6Intent = new Intent("com.kve.splash.TUTORIAL6");
					startActivity(tut6Intent);
				}
			
			});
	
	
	}

		@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
		}

		
		public boolean onCreateOptionsMenu(Menu menu)
		{
			super.onCreateOptionsMenu(menu);
			MenuInflater awesome = getMenuInflater();
			awesome.inflate(R.menu.main_menu,  menu);
			return true;
			
		}
		
		public boolean onOptionsItemSelected(MenuItem item)
		{
			super.onOptionsItemSelected(item);
			
			switch (item.getItemId()) {
			case R.id.menuSweet:
				Intent sweetIntent = new Intent("com.kve.splash.SWEET");
				startActivity(sweetIntent);
				return true;
			case R.id.menuToast:
				Toast toastMessage = Toast.makeText(SplashMenu.this, "Mmm Toast", Toast.LENGTH_LONG);
				toastMessage.show();
					return true;

			default:
				break;
			}
			
		
		return false;
		}


	}

