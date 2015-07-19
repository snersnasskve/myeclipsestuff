package com.kve.chorerota;

import com.kve.chorerota.data.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class ChoreDetailActivity extends Activity implements OnClickListener {

	EditText		etName;
	EditText		etNo;
	Spinner			spUnits;
	static TextView	tvBaseDate;
	static TextView	tvBaseTime;
	CheckBox		cbNotify;
	CheckBox		cbReqDismissal;
	Button			btnSave;
	Button			btnReset;
	Button			btnDelete;
	

	ArrayList <String> 	freqUnitsList;

	//	Keep choreId safe ... -1 means there isn't one
	long 			choreId = -1;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.chore_detail);

		etName			= (EditText) findViewById(R.id.etName);
		etNo			= (EditText) findViewById(R.id.etNo);
		spUnits			= (Spinner)  findViewById(R.id.spUnits);
		tvBaseDate		= (TextView) findViewById(R.id.tvBaseDate);
		tvBaseTime		= (TextView) findViewById(R.id.tvBaseTime);
		cbNotify		= (CheckBox) findViewById(R.id.cbNotify);
		cbReqDismissal	= (CheckBox) findViewById(R.id.cbReqDismissal);
		btnSave			= (Button) 	 findViewById(R.id.btnSave);
		btnReset			= (Button) 	 findViewById(R.id.btnReset);
			btnDelete		= (Button)   findViewById(R.id.btnDelete);

		String 	tempUnits[]		= getResources().getStringArray(R.array.freq_units);
		List<String> tempList 	=  Arrays.asList(tempUnits);
		freqUnitsList			=  new ArrayList <String> (tempList);

		//	If we received an intent, check it for data, if theres data then display it
		Intent recIntent = getIntent();
		showChoreData(recIntent);

	}

	protected void showChoreData(Intent recIntent)
	{
		// Placeholder method;
		// Make a new intent
		//	Pack up the screen values
		//	Return to sender
		
	}

	public void changeDate(View v)
	{

	}


	public void changeTime(View v)
	{

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// check its the save button and save
	}

	void deleteChore (View v)
	{
		//	Head back gracefully 
		//	Only applicable to Edit page
	}
	
	protected HashMap<String, Object> packDataForDb()
	{
		Float freq = Float.parseFloat(etNo.getText().toString());
		
	HashMap<String, Object> queryValuesMap = new HashMap<String, Object>();
	queryValuesMap.put("choreId",		"" + choreId);
	queryValuesMap.put("choreName",		etName.			getText().toString());
	queryValuesMap.put("baseDate",		tvBaseDate.		getText().toString());
	queryValuesMap.put("baseTime",		tvBaseTime.		getText().toString());
	queryValuesMap.put("timeNo",		freq);
	String testVal = freqUnitsList.get((int) spUnits.getSelectedItemId());
	queryValuesMap.put("timeUnit",		freqUnitsList.get((int) spUnits.getSelectedItemId()));
	queryValuesMap.put("toNotify",		(cbNotify.		isChecked() ? 1 : 0));
	queryValuesMap.put("reqDismissal",	(cbReqDismissal.isChecked() ? 1 : 0));
	return queryValuesMap;
	}

void returnToMain()
{
	Intent resultIntent = new Intent();
	resultIntent.putExtra("tbc", "");	//	I need to know whether to refresh the screen or not
setResult(RESULT_OK, resultIntent);
finish();
}
	////////////////////////////////////////////////
	//		Date and Time stuff
	////////////////////////////////////////////////

	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "timePicker");
	}

	public void showTimePickerDialog(View v) {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getFragmentManager(), "timePicker");
	}



	public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// Do something with the time chosen by the user
			Calendar chosenTime = Calendar.getInstance();
			chosenTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
			chosenTime.set(Calendar.MINUTE, minute);
			Date sDate = chosenTime.getTime();
			tvBaseTime.setText(ChoreRecord.getDateString(sDate));
		}
	}


	public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
			Calendar chosenDate = Calendar.getInstance();
			chosenDate.set(year, month, day);  //January 30th 2000
			Date sDate = chosenDate.getTime();

			tvBaseDate.setText(ChoreRecord.getDateString(sDate));

		}
	}





}
