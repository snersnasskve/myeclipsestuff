package com.kve.splash;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class DrawingTheBall extends View {

	Rect ourRect;
	Paint blue;
	Paint emptyPaint;
	
	Bitmap bball;
	int xPos;
	int yPos;
	int xDir;
	int yDir;

	public DrawingTheBall(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		ourRect = new Rect();
		blue = new Paint();
		emptyPaint = new Paint();
		bball = BitmapFactory.decodeResource(getResources(), R.drawable.blueball);
		xPos = 0;
		yPos = 0;
		xDir = yDir = 1;
	

	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		ourRect.set(0, 0, canvas.getWidth(), canvas.getHeight()/2);
		blue.setColor(Color.BLUE);
		blue.setStyle(Paint.Style.FILL);
		canvas.drawRect(ourRect, blue);
		
		/*
		if (xPos < canvas.getWidth())
		{
		xPos += 10;
		}
		else 
		{
			xPos = 0;
		}
		if (yPos < canvas.getHeight())
		{
		yPos += 10;
		}
		else
		{
			yPos = 0;
		}
		*/
		xPos += (5 * xDir);
		yPos += (5 * yDir);
			
		//	Reverse the direction
		if (xPos > (canvas.getWidth() - (bball.getWidth()/2)) || xPos < (bball.getWidth()/2))
		{
			xDir *= -1;
		}
		if (yPos > (canvas.getHeight() - (bball.getHeight())/2) || yPos <  (bball.getHeight()/2))
		{
			yDir *= -1;
		}

		
		canvas.drawBitmap(bball, xPos, yPos, emptyPaint);
		invalidate();
		
	}

}
