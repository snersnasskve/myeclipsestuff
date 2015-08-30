package com.kve.agecalc;


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;

import com.home.agecalc01.R;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

//	Future improvements
//	Highlight the description - maybe make it another activity
//	Hide the year
//	Enable personalisation after it's installed
//
public class MainActivity extends Activity {
	
	DatePicker 	dpBirthDate;
	EditText	etBirthYear;
	TextView	tvAgeDescript;
	
	Hashtable<String, String> specialDates;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		dpBirthDate = (DatePicker) 	findViewById(R.id.dpBirthDate);
		etBirthYear = (EditText)	findViewById(R.id.etBirthYear);
		tvAgeDescript = (TextView)	findViewById(R.id.tvAgeDescript);
		
		setUpSpecialDates();
	}

	private void setUpSpecialDates() {
		specialDates = new Hashtable<String, String>();
		specialDates.put("1961-12-30", "You are 33 years old if you are a day!");
		specialDates.put("1986-2-20", "My dear, age does not apply to you, you look fabulous!");
		specialDates.put("1989-5-24", "Mature but a teenager through and through!");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void calculateAge(View view)
	{
		Log.d("log", "calculateAge");
		int dayNo = dpBirthDate.getDayOfMonth();
		int month = (dpBirthDate.getMonth());
		int year;
		try {
			year = Integer.parseInt(etBirthYear.getText().toString());
		} catch (NumberFormatException e) {
			//	Not a number, use the selected year
			year = (dpBirthDate.getYear());
		}
		GregorianCalendar birthDate = new GregorianCalendar(year, month, dayNo);
		int age = getAgeFromDate(birthDate);
		String ageDescriptor = getDescriptorForAge(age, birthDate);
		tvAgeDescript.setText(ageDescriptor);
	}
	
	

	//	Just the age calc
	private int getAgeFromDate(GregorianCalendar birthDate)
	{
	
		GregorianCalendar dateNow 	= new GregorianCalendar();


		int age = dateNow.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
		if ((birthDate.get(Calendar.MONTH) > dateNow.get(Calendar.MONTH))
				|| (birthDate.get(Calendar.MONTH) == dateNow.get(Calendar.MONTH) && birthDate.get(Calendar.DAY_OF_MONTH) > dateNow
				.get(Calendar.DAY_OF_MONTH))) {
			age--;
		}
		return age;

	}
	
	private String getDescriptorForAge(int age, GregorianCalendar birthDate) {
		String ageDescriptor = "You are " + age + " years old!";
		String birthDateString = "" + birthDate.get(Calendar.YEAR) + "-" + (birthDate.get(Calendar.MONTH) +1) + "-" + birthDate.get(Calendar.DAY_OF_MONTH);
		String specialComment = specialDates.get(birthDateString);
		if (null != specialComment)
		{
			ageDescriptor = specialComment;
		}
		else
		{
			if (age < 21)
			{
				ageDescriptor = "You look very mature and responsible - you must be 21 years old";
			}
			else if (age > 40)
			{
				ageDescriptor = "Are you really " + (age - 10) + "? You could pass for 5 years younger!";
			}
				
		}
				
		return ageDescriptor;
	}

	
}
