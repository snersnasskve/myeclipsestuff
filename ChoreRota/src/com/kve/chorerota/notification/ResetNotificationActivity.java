package com.kve.chorerota.notification;

import java.util.ArrayList;
import java.util.HashMap;

import com.kve.chorerota.data.ChoreRecord;

import android.view.View;


public class ResetNotificationActivity extends NotificationActivity {
	
	protected void processNotification()
	{
		
		//	We have a chorerecord called chore
		//	Add the delay time to current time
		//	Save the new time to database
		//	Make a new intent for time in the future
		trImageButtons.setVisibility(View.GONE);
		tvNotifyDescript.setText("You have RESET the notification");
		
    

	}


}
