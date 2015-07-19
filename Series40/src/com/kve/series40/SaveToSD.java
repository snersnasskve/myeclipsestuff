package com.kve.series40;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SaveToSD extends Activity implements OnClickListener {

	EditText 	etSdFileName;
	Button		btnSavePicture;
	Button		btnSaveSound;
	
	boolean isSdAvailable = false, isSdWriteable = false;
	
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.save_to_sd);
		
		etSdFileName = 		(EditText) 	findViewById(R.id.etSdFilename);
		btnSavePicture =  	(Button) 	findViewById(R.id.btnSavePicture);
		btnSaveSound =  	(Button) 	findViewById(R.id.btnSaveSound);
		
		btnSavePicture.setOnClickListener(this);
		btnSaveSound.setOnClickListener(this);
		
		checkSdStuff();
	}
	
	
	private void checkSdStuff() {
		// TODO Auto-generated method stub
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state))
		{
			//	writeable
			isSdAvailable = true;
			isSdWriteable = true;
		}
		else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
		{
			//	writeable
			isSdAvailable = true;
			isSdWriteable = false;
		}
		else
		{
			isSdAvailable = false;
			isSdWriteable = false;

		}
	}


	@Override
	public void onClick(View v) {
		// Can't test on Emulator
		switch (v.getId()) {
		case R.id.btnSavePicture:
			File imagePath = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
			String imageName = etSdFileName.getText().toString();
			File imageFile = new File(imagePath, (imageName + ".png"));
			saveData(R.drawable.image001, imageFile, imagePath);
			break;
		case R.id.btnSaveSound:
			File soundPath = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_MUSIC );
			String soundName = etSdFileName.getText().toString();
			File soundFile = new File(soundPath, (soundName + ".mp4"));
			saveData(R.raw.chicken_dance, soundFile, soundPath);
			break;

		default:
			break;
		}
	}


	

	private void saveData(int resourceToSave, File file, File path) {
		if (isSdAvailable && isSdWriteable)
		{
			
				try {
					path.mkdirs();	//	make or locate it
					InputStream inputStream = getResources().openRawResource(resourceToSave);
					OutputStream outputStream = new FileOutputStream(file);
					byte[] data = new byte[inputStream.available()];
					inputStream.read(data);
					outputStream.write(data);
					inputStream.close();
					outputStream.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//	Will not work on emulator
		}
	}
	
	

}
