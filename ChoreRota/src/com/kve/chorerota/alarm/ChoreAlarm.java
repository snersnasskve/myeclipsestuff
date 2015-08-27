package com.kve.chorerota.alarm;

import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;

//	This one to set the alarm
public class ChoreAlarm {

	public ChoreAlarm()
	{

	}
	
	public void setAlarm(PendingIntent pendingIntent, Date schedTime, Context context)
	{
		//	http://androidideasblog.blogspot.co.uk/2011/07/alarmmanager-and-notificationmanager.html
		Calendar schedCal = Calendar.getInstance();
		schedCal.setTime(schedTime);
		long firstNotiTime = schedCal.getTimeInMillis();
		AlarmManager am = 
				(AlarmManager) context.getSystemService(context.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, firstNotiTime, pendingIntent);
	}

}
