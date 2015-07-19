package com.kve.splash;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class SurfaceViewExample extends Activity implements OnTouchListener {
	
		OurView v;
		Paint emptyPaint;
		
		Bitmap ball, dude;
		float xPos;
		float yPos;
		
		int xDir;
		int yDir;
		
		Sprite sprite;
		
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		v = new OurView(this);
		setContentView(v);
		
		//	Touch
		v.setOnTouchListener(this);
		
		//	Set up the ball
		emptyPaint = new Paint();
		ball 	= BitmapFactory.decodeResource(getResources(), R.drawable.blueball);
		dude	= BitmapFactory.decodeResource(getResources(), R.drawable.sprite_sheet_one);
			xPos = yPos = ball.getWidth();
		xDir = yDir = 1;

	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		v.pause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		v.resume();
	}


	@Override
	public boolean onTouch(View touchView, MotionEvent motionEvent) {
		// TODO Auto-generated method stub
		
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		switch (motionEvent.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_MOVE:
			//	deliberately not break'd
			xPos = motionEvent.getX();
			yPos = motionEvent.getY();

		default:
			//	do nothing
			break;
		}
		return true;		//	True means come back soon
	}
	

	
	
	//////////////////////////////////////////////////////
	//		Thread
	//////////////////////////////////////////////////////

	public class OurView extends SurfaceView implements Runnable	{
		
		final String TAG = "OurView";
		
		Thread runningThread = null;
		SurfaceHolder holder;
		boolean isItOK 		 = false;
		boolean spriteLoaded = false;

		public OurView(Context context) {
			super(context);
			holder = getHolder();

		}

		@Override
		public void run() {
			Log.i(TAG, "run");
			// TODO Auto-generated method stub
			if (!spriteLoaded)
			{
				sprite 		 = new Sprite (OurView.this, dude);
				spriteLoaded = true;
			}

			while (isItOK)
			{
				//	perfom canvas drawing
				if (!holder.getSurface().isValid())
				{
					continue;
				}
				
					

				//	Lock
				Canvas canvas = holder.lockCanvas();
				
				/*		AThis is for ball
				//	Paint
				//	(alpha, R,G,B)
				canvas.drawARGB(255, 150, 150, 10);
				canvas.drawBitmap(ball, xPos - (ball.getWidth()/2), yPos - (ball.getHeight()/2), null);
				*/
				
				//	This is for dude
				spriteDraw(canvas);
				
				
				//	Unlock
				holder.unlockCanvasAndPost(canvas);
						
			}
			
		}
		
		protected void spriteDraw(Canvas canvas)
		{
			//	Draw the background
			canvas.drawARGB(255, 150, 150, 10);
			canvas.drawBitmap(ball, xPos - (ball.getWidth()/2), yPos - (ball.getHeight()/2), null);
			sprite.sDraw(canvas);
		}
		
		public void pause(){
			isItOK = false;
			while (true)
			{
				try {
					runningThread.join();						//Kind of like synchronise so we can deal with it from main app
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			runningThread = null;
		}
		
		public void resume(){
			isItOK = true;
			runningThread = new Thread(this);
			runningThread.start();
		}
	}




}
