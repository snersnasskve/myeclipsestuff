package com.kve.chorerota;

import com.kve.chorerota.data.*;
import com.kve.chorerota.*;
import java.util.Calendar;
import java.util.HashMap;

import android.content.Intent;
import android.view.View;

public class ChoreAdd extends ChoreDetailActivity {

	
	protected void showChoreData(Intent recIntent)
	{
		setDefaultValues();
	}
	
	private void setDefaultValues() {
		// TODO Auto-generated method stub
		choreId				= -1;
		viewPosition		= -1;
		
		String baseDate	 	= ChoreRecord.getDateString(Calendar.getInstance().getTime());
		String baseTime 	= ChoreRecord.getTimeString(Calendar.getInstance().getTime());
		boolean notify 		 = true;
		boolean reqDismissal = true;
		
		tvBaseDate.setText(baseDate);
		tvBaseTime.setText(baseTime);
			
		cbNotify.setChecked(notify);
		cbReqDismissal.setChecked(reqDismissal);
		
		spUnits.setSelection(ChoreMainActivity.freqUnitsList.indexOf("Days"));
		
		btnReset.setVisibility(View.GONE);
		btnDelete.setVisibility(View.GONE);
	
	}

	public void saveChore (View v)
	{
		//	Make a new chore
		HashMap<String, Object> insertValuesMap = packDataForDb();

		ChoreMainActivity.database.insertChore(insertValuesMap);		

		returnToMain();
	}
	
}
