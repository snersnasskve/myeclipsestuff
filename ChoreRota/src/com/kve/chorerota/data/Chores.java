package com.kve.chorerota.data;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;

import com.kve.chorerota.ChoreMainActivity;
import com.kve.chorerota.schedule.ChoreScheduler;

public class Chores {

	//	Chores is a container for ChoreRecords
	//	All chores should be accessed through this class
	private static ArrayList<ChoreRecord> chores;

	private static DBTools 		database; 
	private Context mContext;

	public Chores(Context context)
	{
		chores = new ArrayList<ChoreRecord>();
		mContext = context;
		database = new DBTools(mContext);

	}

	////////////////////////////////////////////////
	//		Scheduling stuff
	////////////////////////////////////////////////

	public void scheduleChore(int choreId)
	{
		ChoreScheduler scheduler = new ChoreScheduler(mContext);
		
	
	}

	
	////////////////////////////////////////////////
	//		Database stuff
	////////////////////////////////////////////////


	public ArrayList<ChoreRecord> getChores()
	{
		ArrayList<HashMap<String, Object>> choreMap  = database.getChores();
		for (HashMap<String, Object> choreValues : choreMap)
		{

			ChoreRecord choreRec = new ChoreRecord(
					Integer.parseInt( (String)choreValues.get("choreId")), 
					(String) choreValues.get("choreName"),
					(String) choreValues.get("baseDate"),
					(String) choreValues.get("baseTime"),
					(Float ) choreValues.get("timeNo"),
					(String) choreValues.get("timeUnit"),
					((Integer)choreValues.get("toNotify") == 1) ? true : false,
							((Integer)choreValues.get("reqDismissal")== 1) ? true : false);
			chores.add(choreRec);
		}
		return chores;
	}

	public void updateChore(long choreId, String name, String baseDate, String baseTime,
			Float freq, String timeUnit, int toNotify, int reqDismissal)
	{
		HashMap<String, Object> dbValuesMap = 
				packDataForDb(choreId, name, baseDate, baseTime, freq, timeUnit,
						toNotify, reqDismissal);
		database.updateChore(dbValuesMap);
	}


	public void deleteChore(int choreId)
	{
		database.deleteChore(choreId);
	}


	protected HashMap<String, Object> packDataForDb(long choreId, String name,
			String baseDate, String baseTime, Float freq, String timeUnit,
			int toNotify, int reqDismissal)
			{
		HashMap<String, Object> queryValuesMap = new HashMap<String, Object>();
		queryValuesMap.put("choreId",		"" + choreId);
		queryValuesMap.put("choreName",		name		);
		queryValuesMap.put("baseDate",		baseDate	);
		queryValuesMap.put("baseTime",		baseTime	);
		queryValuesMap.put("timeNo",		freq		);
		queryValuesMap.put("timeUnit",		timeUnit	);
		queryValuesMap.put("toNotify",		toNotify	);
		queryValuesMap.put("reqDismissal",	reqDismissal);
		return queryValuesMap;
			}

}
