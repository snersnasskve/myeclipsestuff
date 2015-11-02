package com.kve.chorerota.notification;

import java.util.Calendar;

import com.kve.chorerota.ChoreMainActivity;
import com.kve.chorerota.R;
import com.kve.chorerota.alarm.*;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;


import android.support.v4.app.NotificationCompat;


public class ChoreNotification {

	static int CHORE_NOTIFY_ID = 0;

	String notiTitle;
	String notiText;
	String notiReset;
	String notiSnooze;
	String notiCancel;
	
	PendingIntent resetPending;
	PendingIntent snoozePending;
	PendingIntent cancelPending;
	PendingIntent defaultPending;
	
	public ChoreNotification()
	{

	}
	
	public void scheduleChore(String choreName, long choreId, Calendar schedTime, Context context)
	{
		Intent alarmIntent = new Intent(context, ChoreAlarmReceiver.class);
		alarmIntent.putExtra("choreId", choreId);
		alarmIntent.putExtra("choreName", choreName);
		PendingIntent alarmPending = PendingIntent.getActivity(context, 0, alarmIntent, 0);

		ChoreAlarm alarm = new ChoreAlarm();
		alarm.setAlarm(alarmPending, schedTime, context);
	}
	
	
	public void getChoreNotification(String choreName, long choreId, Context context)
	{
		NotificationManager notificationManager = (NotificationManager) 
				context.getSystemService(Context.NOTIFICATION_SERVICE); 

	
		Intent defaultIntent = setupNotiPendingIntents(choreId, context);

		setupNotiStrings(choreName, context);

		//	Build notification
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
		.setSmallIcon(R.drawable.snooze)
		.setContentTitle(notiTitle)
		.setContentText(notiText)
		.setAutoCancel(true);

		//	Set intents for each action
		mBuilder.setContentIntent(defaultPending);
		mBuilder.addAction(R.drawable.tick, 	notiReset, resetPending);
		mBuilder.addAction(R.drawable.snooze, 	notiSnooze, snoozePending);
		mBuilder.addAction(R.drawable.cross, 	notiCancel, cancelPending);

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(ChoreMainActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(defaultIntent);


		// hide the notification after its selected
		// noti.flags |= Notification.FLAG_AUTO_CANCEL;


		notificationManager.notify(0, mBuilder.build());	
	}


	// prepare intents which are triggered if the notification is selected
	private Intent setupNotiPendingIntents(long choreId, Context context) {
		Intent resetIntent = new Intent(context, ResetNotificationActivity.class);
		resetIntent.putExtra("choreId", choreId);
		resetPending = PendingIntent.getActivity(context, 0, resetIntent, 0);

		Intent snoozeIntent = new Intent(context, SnoozeNotificationActivity.class);
		snoozeIntent.putExtra("choreId", choreId);
		snoozePending = PendingIntent.getActivity(context, 0, snoozeIntent, 0);

		Intent cancelIntent = new Intent(context, CancelNotificationActivity.class);
		cancelIntent.putExtra("choreId", choreId);
		cancelPending = PendingIntent.getActivity(context, 0, cancelIntent, 0);

		Intent defaultIntent = new Intent(context, NotificationActivity.class);
		defaultIntent.putExtra("choreId", choreId);
		defaultPending = PendingIntent.getActivity(context, 0, defaultIntent, 0);
		return defaultIntent;
	}


	//	Strings for notification
	private void setupNotiStrings(String choreName, Context context) {
		//	Text strings
		notiTitle = context.getResources().getString(R.string.chore_notification);
		notiText  = context.getResources().getString(R.string.notification_before_chore)
				+ choreName + 
				context.getResources().getString(R.string.notification_after_chore);
		notiReset  = context.getResources().getString(R.string.done);
		notiSnooze = context.getResources().getString(R.string.chore_notification);
		notiCancel = context.getResources().getString(R.string.chore_notification);
	}


}