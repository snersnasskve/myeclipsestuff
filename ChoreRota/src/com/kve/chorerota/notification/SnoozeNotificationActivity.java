package com.kve.chorerota.notification;

import android.view.View;



public class SnoozeNotificationActivity extends NotificationActivity {
	
	
	
	protected void processNotification()
	{
		trImageButtons.setVisibility(View.GONE);
		tvNotifyDescript.setText("You have SNOOZED the notification");
	}


}
