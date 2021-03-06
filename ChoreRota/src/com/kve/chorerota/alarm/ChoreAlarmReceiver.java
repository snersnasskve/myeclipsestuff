package com.kve.chorerota.alarm;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.Toast;

public class ChoreAlarmReceiver extends BroadcastReceiver {

	final String CHORE_TAG = "CHORE TAG";
	final public static String ONE_TIME = "onetime";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
	//	Acquire the lock
	//	wakeLock.acquire();
		
		//	You can do the processing here
		Bundle extras = intent.getExtras();
		StringBuilder message = new StringBuilder();
		
		if (null != extras && extras.getBoolean(ONE_TIME, Boolean.FALSE))
		{
			//	Make sure this intent has been sent by the one-time timer
			message.append("One tme tmer : ");
		}
		
		DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT, Locale.UK);
		message.append(formatter.format(new Date()));
		
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		
		
		//	Release the lock
	//	wakeLock.release();
		
	}

	
	public void setOnetimeTimer(Context context)
	{
		AlarmManager am = 
				(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, ChoreAlarmReceiver.class);
		intent.putExtra(ONE_TIME, Boolean.TRUE);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ 20000, pendingIntent);
		
	}
}
