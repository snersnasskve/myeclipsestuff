package com.kve.splash;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class Tutorial5 extends Activity{

	VideoView vid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_layout);
		vid = (VideoView)findViewById(R.id.video);
		String urlPath = "android.resource://"+ getPackageName() + "/" + R.raw.leopard;
		vid.setVideoURI(Uri.parse (urlPath));
		vid.start();
	
		//	There;s no media controller, I don't care enough.
		MediaController mc = new MediaController(this);
		mc.setMediaPlayer(vid);
		vid.setMediaController(mc);
		

	}

}
