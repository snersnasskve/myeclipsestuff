package com.kve.ramblerswalks;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

public class Weekdays extends Activity implements OnClickListener {

	//CheckBox cbMonday, cbTuesday, cbWednesday, cbThursday, cbFriday, cbSaturday, cbSunday;
	CheckBox tickBoxes[] = new CheckBox[7];
	String dayArray[] = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	Button btnWeekdays;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weekdays);
		
		tickBoxes[0] 	= (CheckBox) findViewById(R.id.cbMonday);
		tickBoxes[1] 	= (CheckBox) findViewById(R.id.cbTuesday);
		tickBoxes[2] 	= (CheckBox) findViewById(R.id.cbWednesday);
		tickBoxes[3] 	= (CheckBox) findViewById(R.id.cbThursday);
		tickBoxes[4] 	= (CheckBox) findViewById(R.id.cbFriday);
		tickBoxes[5] 	= (CheckBox) findViewById(R.id.cbSaturday);
		tickBoxes[6] 	= (CheckBox) findViewById(R.id.cbSunday);
		btnWeekdays 	= (Button) 	 findViewById(R.id.btnWeekdays);
	
		btnWeekdays.setOnClickListener(this);
		
		Intent intent = getIntent();
		String weekdaysString = intent.getExtras().getString("weekdays");
		populateTickBoxes(weekdaysString);
	}

	

	@Override
	public void onClick(View arg0) {
		
		ArrayList<String> chosenDays = new ArrayList<String>();
		for (int wCounter = 0; wCounter < 7; wCounter++)
		{
			if (tickBoxes[wCounter].isChecked())
			{
				chosenDays.add(dayArray[wCounter]);
			}
		}
		Intent responseIntent = new Intent();
		//String chosenDaysArray[] = chosenDays.toArray(new String[chosenDays.size()]);
		responseIntent.putExtra("weekdays", chosenDays);
		this.setResult(RESULT_OK, responseIntent);
		finish();
	}

	private void populateTickBoxes(String weekdaysString) {
		// TODO Auto-generated method stub
		
		for (int dayCounter = 0 ; dayCounter < 7 ; dayCounter++)
		{
			if (weekdaysString.indexOf(dayArray[dayCounter]) >= 0)
			{
				//	Weekday needs a tick
				tickBoxes[dayCounter].setChecked(true);
			}
			else
			{
				tickBoxes[dayCounter].setChecked(false);			
			}
				
		}
	}
	
}
