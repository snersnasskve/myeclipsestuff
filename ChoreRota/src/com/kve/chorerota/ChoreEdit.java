package com.kve.chorerota;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.kve.chorerota.alarm.*;
import com.kve.chorerota.data.ChoreRecord;
import com.kve.chorerota.notification.ChoreNotification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;

public class ChoreEdit extends ChoreDetailActivity {

	
	
	protected void showChoreData(Intent recIntent) {
		choreId				= recIntent.getExtras().getInt  ("choreId");
		viewPosition		=  recIntent.getExtras().getInt  ("postion");

		String choreName 	= recIntent.getExtras().getString("choreName");
		Float  freqNo 	 	= recIntent.getExtras().getFloat ("freqNo");
		String freqUnits 	= recIntent.getExtras().getString("freqUnits");
		String baseDate	 	= recIntent.getExtras().getString("baseDate");
		String baseTime 	= recIntent.getExtras().getString("baseTime");
		boolean notify 		 = (0 == recIntent.getExtras().getInt("notify")) 		? false : true;
		boolean reqDismissal = (0 == recIntent.getExtras().getInt("reqDismissal")) 	? false : true;
		
		etName.setText(choreName);
		etNo.setText(freqNo .toString());
		tvBaseDate.setText(baseDate);
		tvBaseTime.setText(baseTime);
			
		cbNotify.setChecked(notify);
		cbReqDismissal.setChecked(reqDismissal);
		
		spUnits.setSelection(ChoreMainActivity.freqUnitsList.indexOf(freqUnits));
	}

	
	public void saveChore (View v)
	{
		//	Make a new chore
		//HashMap<String, Object> editValuesMap = packDataForDb();

		updateChore();
		returnToMain();
	}
	
	public void resetChore (View v)
	{
		//	Set the times to current
		
		tvBaseDate.setText(ChoreRecord.getDateString(Calendar.getInstance().getTime()));
		tvBaseTime.setText(ChoreRecord.getTimeString(Calendar.getInstance().getTime()));

		//	update database
		//HashMap<String, Object> editValuesMap = packDataForDb();
		//ChoreMainActivity.database.updateChore(editValuesMap);		
		updateChore();
		
		scheduleChore();
		
		//	Launch notification
//ChoreNotification choreNotification = new ChoreNotification();
//choreNotification.getChoreNotification(etName.getText().toString(), choreId, this);
		ChoreNotification notification = new ChoreNotification();
		notification.scheduleChore(etName.getText().toString(),
				choreId,
				selectedChore.getDateNextDue(),
				getApplicationContext());
//alarm.setOnetimeTimer(getApplicationContext());

		returnToMain();
	}
	
	
	public void deleteChore (View v)
	{
		//	Remove from database
		//	Remove from adapter
		//ChoreMainActivity.database.deleteChore((int)choreId);		
		ChoreMainActivity.mChores.deleteChore(choreId);

		returnToMain();
	}

}


