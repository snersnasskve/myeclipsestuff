package com.kve.chorerota.schedule;

import com.kve.chorerota.data.ChoreRecord;

import android.content.Context;

//	This class is responsible for handling all notifications
//	Other classes should only ever deal through ChoreScheduler
//	It needs to make Pending Intents so needs a context straight off
public class ChoreScheduler {

	private Context mContext;
	
	public ChoreScheduler (Context context)
	{
		mContext = context;
	}
	
	public void schedule(ChoreRecord chore)
	{
		//	Need an intent built with all the details we may need
		//	Date, time, Choreid and Descriptions
	}
}
