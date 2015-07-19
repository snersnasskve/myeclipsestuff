package uk.ac.reading.sis05kol.mooc;

//Other parts of the android libraries that we use
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
//import android.view.MotionEvent;

public class TheGame extends GameThread{

	//Will store the image of a ball
	private Bitmap mBall;
	
	//The X and Y position of the ball on the screen (middle of ball)
	private float mBallX = 0;
	private float mBallY = 0;
	
	//The speed (pixel/second) of the ball in direction X and Y
	private float mBallSpeedX = 0;
	private float mBallSpeedY = 0;
	private float mBallRadiusX;
	private float mBallRadiusY;

	//	The Paddle
	private Bitmap mPaddle;
	private float mPaddleX;
	private float mPaddleRadiusX;
	private float mPaddleRadiusY;
	
	private float mPaddleCrashingDistance, mSmileyCrashingDistance;
	
	//	Smiley ball
	private Bitmap mSmileyBall;
	private float mCentre;
	private float mSmileyRadiusX;
	private float mSmileyRadiusY;
	

	//This is run before anything else, so we can prepare things here
	public TheGame(GameView gameView) {
		//House keeping
		super(gameView);
		
		//Prepare the image so we can draw it on the screen (using a canvas)
		mBall = BitmapFactory.decodeResource
				(gameView.getContext().getResources(), 
				R.drawable.small_red_ball);
		mPaddle = BitmapFactory.decodeResource
				(gameView.getContext().getResources(), 
				R.drawable.yellow_ball);
		mSmileyBall = BitmapFactory.decodeResource
				(gameView.getContext().getResources(), 
				R.drawable.smiley_ball);
	
		mBallRadiusX = 	(mBall.getWidth()) / 2;
		mBallRadiusY = 	(mBall.getHeight()) / 2;
		mPaddleRadiusX = (mPaddle.getWidth()) / 2;
		mPaddleRadiusY = (mPaddle.getHeight()) / 2;
		mSmileyRadiusX = (mSmileyBall.getWidth()) / 2;
		mSmileyRadiusY = (mSmileyBall.getHeight()) / 2;

		//	Made X and Y so you could use oval etc
		//	But no idea of the maths here if you did
		mPaddleCrashingDistance = (mBallRadiusY + mPaddleRadiusY) * (mBallRadiusY + mPaddleRadiusY);
		mSmileyCrashingDistance = (mBallRadiusY + mSmileyRadiusY) * (mBallRadiusY + mSmileyRadiusY);
	}
	
	//This is run before a new game (also after an old game)
	@Override
	public void setupBeginning() {
		//Initialise speeds
		mBallSpeedX = 100; 
		mBallSpeedY = 100;
		
		//Place the ball in the middle of the screen.
		//mBall.Width() and mBall.getHeigh() gives us the height and width of the image of the ball
		mBallX = mCanvasWidth / 2;
		mBallY = mCanvasHeight / 2;
		
		mPaddleX = mCanvasWidth / 2;
		mCentre = mCanvasWidth / 2;
	}

	@Override
	protected void doDraw(Canvas canvas) {
		//If there isn't a canvas to draw on do nothing
		//It is ok not understanding what is happening here
		if(canvas == null) return;
		
		super.doDraw(canvas);
		
		//draw the image of the ball using the X and Y of the ball
		//drawBitmap uses top left corner as reference, we use middle of picture
		//null means that we will use the image without any extra features (called Paint)
		canvas.drawBitmap(mBall, mBallX - mBallRadiusX, mBallY - mBallRadiusY, null);
		canvas.drawBitmap(mPaddle, mPaddleX - (mPaddleRadiusX) , mCanvasHeight - (mPaddleRadiusY), null);
		canvas.drawBitmap(mSmileyBall, mCentre - (mSmileyRadiusX) , 0, null);
}
	
	//This is run whenever the phone is touched by the user
	@Override
	protected void actionOnTouch(float x, float y) {
	//	mPaddleX = x - mPaddleRadiusX;
		mPaddleX = x;
		}
	
	
	//This is run whenever the phone moves around its axises 
	@Override
	protected void actionWhenPhoneMoved(float xDirection, float yDirection, float zDirection) {
		if(mPaddleX >= 0 && mPaddleX <= mCanvasWidth) {
			mPaddleX = mPaddleX - xDirection;
			if(mPaddleX < 0) mPaddleX = 0;
			if(mPaddleX > mCanvasWidth) mPaddleX = mCanvasWidth;
			}
	}
	
	//This is run just before the game "scenario" is printed on the screen
	@Override
	protected void updateGame(float secondsElapsed) {
		
	
		//	If the ball is leaving the screen, reverse the relevant speed
		if ((mBallX < mBallRadiusX && mBallSpeedX < 0) || 
				(mBallX > mCanvasWidth - mBallRadiusX && mBallSpeedX > 0))
			mBallSpeedX = -mBallSpeedX;
		if (mBallY < mBallRadiusY && mBallSpeedY < 0) 
			mBallSpeedY = -mBallSpeedY;
		if (mBallY > mCanvasHeight  && mBallSpeedY > 0)
		{
			//	Out
			setState(GameThread.STATE_LOSE);
		}
		//	Check for crashing 
		if (mBallSpeedY > 0)
		{
			//	going down
			float xDistance = mPaddleX - mBallX;
			float yDistance = mCanvasHeight - mBallY;
			float distanceBetweenBallAndPaddle =  (xDistance * xDistance) + (yDistance * yDistance);
			//	Dealing only in squared for Pythagoras
			if (mPaddleCrashingDistance >= distanceBetweenBallAndPaddle )
			{
				//	Velocity for the old direction
				float velocityOfBall = (float) Math.sqrt((mBallSpeedX * mBallSpeedX) + (mBallSpeedY * mBallSpeedY));
				
				//	Actual distance apart
				mBallSpeedX = mBallX - mPaddleX;
				mBallSpeedY = mBallY - mCanvasHeight;
				
				float newVelocity = (float) Math.sqrt((mBallSpeedX * mBallSpeedX) + (mBallSpeedY * mBallSpeedY));

				//	Physics is beyond me
				mBallSpeedX = mBallSpeedX * velocityOfBall / newVelocity;
				mBallSpeedY = mBallSpeedY * velocityOfBall / newVelocity;
						
			}
		}
		else //	Ball could be going up or down
		{
			//	Bouncong off an area that is too small - think I might have muddled ball and smiley somehow
			//	going down
			float xDistance = mCentre - mBallX;
			float yDistance =  mSmileyRadiusY - mBallY;		//	This is because the centre of the ball is at 0 plus mSmileyRadiusY
			float distanceBetweenBallAndSmiley =  (xDistance * xDistance) + (yDistance * yDistance);
			//	Dealing only in squared for Pythagoras
			if (mSmileyCrashingDistance >= distanceBetweenBallAndSmiley )
			{
				//	Velocity for the old direction
				float velocityOfBall = (float) Math.sqrt((mBallSpeedX * mBallSpeedX) + (mBallSpeedY * mBallSpeedY));
				
				//	Actual distance apart
				mBallSpeedX = mBallX - mCentre;
				mBallSpeedY = mBallY ;
				
				float newVelocity = (float) Math.sqrt((mBallSpeedX * mBallSpeedX) + (mBallSpeedY * mBallSpeedY));

				//	Physics is beyond me
				mBallSpeedX = mBallSpeedX * velocityOfBall / newVelocity;
				mBallSpeedY = mBallSpeedY * velocityOfBall / newVelocity;
				
				updateScore(1);
					
			}
		}

		//Move the ball's X and Y using the speed (pixel/sec)
		mBallX = mBallX + secondsElapsed * mBallSpeedX;
	    mBallY = mBallY + secondsElapsed * mBallSpeedY;
	}
}

// This file is part of the course "Begin Programming: Build your first mobile game" from futurelearn.com
// Copyright: University of Reading and Karsten Lundqvist
// It is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// It is is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// 
// You should have received a copy of the GNU General Public License
// along with it.  If not, see <http://www.gnu.org/licenses/>.
