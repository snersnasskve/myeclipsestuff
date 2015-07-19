package com.kve.chorerota.notification;

import android.view.View;


public class CancelNotificationActivity extends NotificationActivity {
	
	protected void processNotification()
	{
		trImageButtons.setVisibility(View.GONE);
		tvNotifyDescript.setText("You have CANCELLED the notification");
	}


}
