package com.kve.splash;

import com.kve.splash.SurfaceViewExample.OurView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Sprite {
	
	final String TAG = "Sprite";
	int xPos, yPos;
	int xSpeed, ySpeed;
	int dudeWidth, dudeHeight;
	int dudeDirection, actionCounter, scaleDirection = 2;
	int scaleFactor = 1 ;
	
	
	Bitmap dudeSheet;
	OurView surfView;

	public Sprite(OurView ourView, Bitmap aDudeSheet) {
		Log.i(TAG, "Constructor");
		dudeSheet 	= aDudeSheet;
		surfView 	= ourView;
		dudeWidth	= (dudeSheet.getWidth()) / 4;
		dudeHeight	= (dudeSheet.getHeight()) / 4;
		xPos = yPos = 0;
		xSpeed 		= 5;
		ySpeed		= 0;
	}

	void sDraw(Canvas canvas)
	{
		//	Cast um to floats and cast um back
		int sheetXPos = (actionCounter  * dudeWidth);
		int sheetYPos = (dudeDirection  * dudeHeight);
		//	The whole size of the bitmap - all the dudes
		update();

		Rect source 	= new Rect(sheetXPos, sheetYPos, dudeWidth + sheetXPos, dudeHeight + sheetYPos);
		Rect scaling 	= new Rect(xPos, yPos, xPos + (dudeWidth), yPos + (dudeHeight));
		canvas.drawBitmap(dudeSheet,  source,  scaling, null);
	}

	private void update() {
		
		//	Check if it has hit the wall
		//	0 = down
		//	1 = left
		//	2 = right
		//	3 = up
		if (xPos > surfView.getWidth() - dudeWidth - xSpeed)
		{	//	right
			xSpeed = 0;
			ySpeed = 5;
			dudeDirection = 0;
		}	//	left
		if (yPos > surfView.getHeight() - dudeHeight - ySpeed)
		{
			xSpeed = -5;
			ySpeed = 0;
			dudeDirection = 1;
	}	// up
		if (xPos + xSpeed < 0 )
		{
			xSpeed = 0;
			ySpeed = -5;
			dudeDirection = 3;
	}	//right
		if (yPos + ySpeed < 0 )
		{
			xSpeed = 5;
			ySpeed = 0;
			dudeDirection = 2;
		}

		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		xPos += xSpeed;
		yPos += ySpeed;
		
		
	actionCounter = (++actionCounter) % 4;
		
	}
}
