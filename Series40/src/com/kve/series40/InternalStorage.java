package com.kve.series40;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;




public class InternalStorage extends Activity implements OnClickListener
{
	Button btnSave;
	EditText etName, etEntry;
	private String saveFileName, journalEntry;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.internal_storage);
		
		btnSave = (Button)  findViewById(R.id.btnSaveStore);
		etName	= (EditText)findViewById(R.id.etFileName);
		etEntry	= (EditText)findViewById(R.id.etJournalEntry);
		
		btnSave.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		saveFileName = etName.getText().toString();
		if (saveFileName.contentEquals(""))
		{
			saveFileName = "Untitled";
		}
		journalEntry = etEntry.getText().toString();

		try {
			FileOutputStream outputStream = openFileOutput(saveFileName, Context.MODE_PRIVATE);
			outputStream.write(journalEntry.getBytes());
			outputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finish();
	}
}
