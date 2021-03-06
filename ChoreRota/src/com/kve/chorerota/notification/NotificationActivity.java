package com.kve.chorerota.notification;

import java.util.HashMap;

import com.kve.chorerota.ChoreMainActivity;
import com.kve.chorerota.R;
import com.kve.chorerota.data.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;

public class NotificationActivity extends Activity {

	TextView tvNotifyDescript;
	
	ImageButton ibReset;
	ImageButton ibSnooze;
	ImageButton ibCancel;
	
	TableRow	trImageButtons;

	//Long		choreId;
	
	ChoreRecord chore;
	DBTools		database;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.notification_received);

		tvNotifyDescript = (TextView) 	findViewById(R.id.tvNotifyDescript);
		ibReset			 = (ImageButton)findViewById(R.id.ibReset);
		ibSnooze		 = (ImageButton)findViewById(R.id.ibSnooze);
		ibCancel		 = (ImageButton)findViewById(R.id.ibCancel);
		trImageButtons	 = (TableRow)	findViewById(R.id.trImageButtons);
	
		Intent recIntent = getIntent();
		Long choreId = recIntent.getLongExtra("choreId", -1l);
		
		setupChoreRecord(choreId);
		
		//	Hand over to subclasses
		processNotification();
		
	
	}
	
	private void setupChoreRecord(Long choreId) {
	      database = new DBTools(this);
	      
		HashMap<String, Object> choreData = database.getChore("" + choreId);
  		chore = new ChoreRecord(
				(Long) choreData.get("choreId"), 
				(String) choreData.get("choreName"),
				(String) choreData.get("baseDate"),
				(String) choreData.get("baseTime"),
				(Float ) choreData.get("timeNo"),
				(String) choreData.get("timeUnit"),
				((Integer)choreData.get("toNotify") == 1) ? true : false,
						((Integer)choreData.get("reqDismissal")== 1) ? true : false);

	}

	protected void processNotification()
	{
		//tvNotifyDescript.setText("Implementing the base class - summat went wrong!");
	}

	public void notificationReset(View v)
	{
		Intent resetIntent = new Intent(this, ResetNotificationActivity.class);
		startActivity(resetIntent);
		finish();

	}
	
	public void notificationSnooze(View v)
	{
		Intent snoozeIntent = new Intent(this, SnoozeNotificationActivity.class);
		startActivity(snoozeIntent);
		finish();
		
	}
	
	public void notificationCancel(View v)
	{
		Intent cancelIntent = new Intent(this, CancelNotificationActivity.class);
		startActivity(cancelIntent);
		finish();

	}

	
}
