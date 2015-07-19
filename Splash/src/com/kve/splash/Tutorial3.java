package com.kve.splash;

import java.io.IOException;

import android.app.Activity;
import android.app.WallpaperManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class Tutorial3 extends Activity implements OnClickListener {
	
	ImageView display;
	int selectedImageId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//	full screen
		requestWindowFeature(Window.FEATURE_NO_TITLE );
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.wallpaper);
		
		display = (ImageView)findViewById(R.id.ivDisplay);
		ImageView img1 = (ImageView)findViewById(R.id.imageView1);
		ImageView img2 = (ImageView)findViewById(R.id.imageView2);
		ImageView img3 = (ImageView)findViewById(R.id.imageView3);
		ImageView img4 = (ImageView)findViewById(R.id.imageView4);
		ImageView img5 = (ImageView)findViewById(R.id.imageView5);
		ImageView img6 = (ImageView)findViewById(R.id.imageView6);
		ImageView img7 = (ImageView)findViewById(R.id.imageView7);
		ImageView img8 = (ImageView)findViewById(R.id.imageView8);
		ImageView img9 = (ImageView)findViewById(R.id.imageView9);
		ImageView img10 = (ImageView)findViewById(R.id.imageView10);
		ImageView img11 = (ImageView)findViewById(R.id.imageView11);
		ImageView img12 = (ImageView)findViewById(R.id.imageView12);
		Button wallPaperButton = (Button) findViewById(R.id.setWallpaper);
		
		selectedImageId = R.drawable.cat;
		
		img1.setOnClickListener(this);
		img2.setOnClickListener(this);
		img3.setOnClickListener(this);
		img4.setOnClickListener(this);
		img5.setOnClickListener(this);
		img6.setOnClickListener(this);
		img7.setOnClickListener(this);
		img8.setOnClickListener(this);
		img9.setOnClickListener(this);
		img10.setOnClickListener(this);
		img11.setOnClickListener(this);
		img12.setOnClickListener(this);
		wallPaperButton.setOnClickListener(this);
	
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()){
		case R.id.imageView1:
			display.setImageResource(R.drawable.apple);
			selectedImageId = R.drawable.apple;
			break;
		case R.id.imageView2:
			display.setImageResource(R.drawable.cat_tongue);
			selectedImageId = R.drawable.cat_tongue;
			break;
		case R.id.imageView3:
			display.setImageResource(R.drawable.cat);
			selectedImageId = R.drawable.cat;
			break;
		case R.id.imageView4:
			display.setImageResource(R.drawable.chicken);
			selectedImageId = R.drawable.chicken;
			break;
		case R.id.imageView5:
			display.setImageResource(R.drawable.dog);
			selectedImageId = R.drawable.dog;
			break;
		case R.id.imageView6:
			display.setImageResource(R.drawable.horse);
			selectedImageId = R.drawable.horse;
			break;
		case R.id.imageView7:
			display.setImageResource(R.drawable.lion);
			selectedImageId = R.drawable.lion;
			break;
		case R.id.imageView8:
			display.setImageResource(R.drawable.panda);
			selectedImageId = R.drawable.panda;
			break;
		case R.id.imageView9:
			display.setImageResource(R.drawable.rabbit);
			selectedImageId = R.drawable.rabbit;
			break;
		case R.id.imageView10:
			display.setImageResource(R.drawable.snow);
			selectedImageId = R.drawable.snow;
			break;
		case R.id.imageView11:
			display.setImageResource(R.drawable.star);
			selectedImageId = R.drawable.star;
			break;
		case R.id.imageView12:
			display.setImageResource(R.drawable.universe);
			selectedImageId = R.drawable.universe;
			break;
		case R.id.setWallpaper:
			WallpaperManager wallManager =  WallpaperManager.getInstance(getApplicationContext());
			try {
				wallManager.setResource(selectedImageId);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
