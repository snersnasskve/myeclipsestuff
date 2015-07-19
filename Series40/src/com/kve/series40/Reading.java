package com.kve.series40;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Reading extends Activity implements OnClickListener {
	
	Spinner spinner;
	TextView title, entry;
	Button btnLoad;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reading);
		
		spinner = (Spinner) findViewById(R.id.sSpinner);
		title	= (TextView)findViewById(R.id.tvTitle);
		entry	= (TextView)findViewById(R.id.tvEntry);
		btnLoad = (Button)  findViewById(R.id.loadEntry);
		
		getFileNames();
		
		btnLoad.setOnClickListener(this);
		spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				title.setText((parent.getItemAtPosition(position)).toString());
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}});
		
}

	
	private void getFileNames(){
		String[] fileNames = getApplicationContext().fileList();
		List<String> fileList = new ArrayList<String>();
		for (int fCounter = 0 ; fCounter < fileNames.length ; fCounter++){
			fileList.add(fileNames[fCounter]);
		}
		//	We now have a proper list
		ArrayAdapter<String> aaFileList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, fileList);
		spinner.setAdapter(aaFileList);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String selectFile = String.valueOf(spinner.getSelectedItem());
		openFile(selectFile);
	}


	private void openFile(String selectFile)  {
		// TODO Auto-generated method stub
		String value = "";
		FileInputStream inputStream;

		try {
			inputStream = openFileInput(selectFile);
			byte[] input = new byte[inputStream.available()];
			while (-1 != inputStream.read(input))
			{
				value += new String(input);
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		entry.setText(value);
	}

}
