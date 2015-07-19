package com.kve.chorerota.data;


import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBTools extends SQLiteOpenHelper {

	private SQLiteDatabase mWritable;
	private String tableName = "chores";

	public DBTools(Context appContext) {
		super(appContext, "chorerota.db", null, 2);
	}

		@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//	Err on the side of too many fields - can always auto fill
		String query = "CREATE TABLE "+ tableName +" ( chore_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"chore_name TEXT, base_date TEXT, base_time TEXT, time_no REAL, time_unit TEXT, " + 
				"to_notify INT, req_dismissal INT)" ;
		db.execSQL(query);
	}

	
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
		
			String query = "DROP TABLE IF EXISTS "+ tableName;
			db.execSQL(query);
			onCreate(db);
		}
	


	public void insertChore(HashMap<String, Object> choreValues)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("chore_name", 	(String) choreValues.get("choreName"));
		values.put("base_date", 	(String) choreValues.get("baseDate"));
		values.put("base_time", 	(String) choreValues.get("baseTime"));
		values.put("time_no", 		(Float ) choreValues.get("timeNo"));
		values.put("time_unit",		(String) choreValues.get("timeUnit"));
		values.put("to_notify", 	(Integer)choreValues.get("toNotify"));
		values.put("req_dismissal", (Integer)choreValues.get("reqDismissal"));

		db.insert(tableName, null, values);
		db.close();
	}
	
	public int updateChore(HashMap<String, Object> choreValues)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("chore_name", 	(String) choreValues.get("choreName"));
		values.put("base_date", 	(String) choreValues.get("baseDate"));
		values.put("base_time", 	(String) choreValues.get("baseTime"));
		values.put("time_no", 		(Float ) choreValues.get("timeNo"));
		values.put("time_unit",		(String) choreValues.get("timeUnit"));
		values.put("to_notify", 	(Integer)choreValues.get("toNotify"));
		values.put("req_dismissal", (Integer)choreValues.get("reqDismissal"));
	
		return db.update(tableName, values, 
				"chore_id = ?", new String[] {(String)choreValues.get("choreId")});
		
	}

	public void deleteChore(int id)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		String deleteQuery = "DELETE FROM "+ tableName + " WHERE chore_id='" + id + "'";
		db.execSQL(deleteQuery);			
	}

	
	public HashMap<String, Object> getChore(String id)
	{
		HashMap<String, Object> choreMap = new HashMap<String, Object>();
			
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM "+ tableName + " WHERE chore_id=?" ;
		String params[] = {id};
	//Cursor cursor = Query(selectQuery, params);
		Cursor cursor = db.rawQuery(selectQuery, params);
		
		if (cursor.moveToFirst()){
			choreMap.put("choreId", 		cursor.getLong(0));
			choreMap.put("choreName",	 	cursor.getString(1));
			choreMap.put("baseDate", 		cursor.getString(2));
			choreMap.put("baseTime", 		cursor.getString(3));
			choreMap.put("timeNo", 			cursor.getFloat (4));
			choreMap.put("timeUnit", 		cursor.getString(5));
			choreMap.put("toNotify", 		cursor.getInt(6));
			choreMap.put("reqDismissal", 	cursor.getInt(7));
		}
		cursor.close();
		return choreMap;
	}

	//	Get filtered or unfiltered list
	public ArrayList<HashMap<String, Object>> getChores()
	{
		ArrayList<HashMap<String, Object>> choreArray = new ArrayList<HashMap<String, Object>>();
		
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM "+ tableName + " ORDER BY chore_name";
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if (cursor.moveToFirst()){
			do {
				HashMap<String, Object> choreMap = new HashMap<String, Object>();
				choreMap.put("choreId",			cursor.getLong(0));
				choreMap.put("choreName",		cursor.getString(1));
				choreMap.put("baseDate", 		cursor.getString(2));
				choreMap.put("baseTime", 		cursor.getString(3));
				choreMap.put("timeNo", 			cursor.getFloat (4));
				choreMap.put("timeUnit", 		cursor.getString(5));
				choreMap.put("toNotify", 		cursor.getInt(6));
				choreMap.put("reqDismissal", 	cursor.getInt(7));
	
				choreArray.add(choreMap);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return choreArray;
	}
	
	/*
	public ArrayList<HashMap<String, Object>> getChores(FilterPrefs filterPrefs)
	{
		ArrayList<HashMap<String, Object>> choreArray = new ArrayList<HashMap<String, Object>>();
		
		SQLiteDatabase db = this.getReadableDatabase();
		String whereClause = getWhereClause(filterPrefs);
		
		String selectQuery = "SELECT * FROM " + tableName + " " + whereClause + " ORDER BY chore_name" ;
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if (cursor.moveToFirst()){
			do {
				HashMap<String, Object> choreMap = new HashMap<String, Object>();
				choreMap.put("choreId",	cursor.getLong(0));
				choreMap.put("choreName",	cursor.getString(1));
				choreMap.put("baseDate", 	cursor.getString(2));
				choreMap.put("timeNo", 		cursor.getFloat(3));
				choreMap.put("timeUnit", 	cursor.getString(4));
				choreMap.put("toNotify", 	cursor.getInt(5));
				choreMap.put("reqDismissal",cursor.getInt(6));
					choreArray.add(choreMap);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return choreArray;
	}
	
	private String getWhereClause(FilterPrefs filterPrefs)
	{
		String whereClause = " WHERE ";
		boolean whereClauseRequired = false;
		
		int favouritesValue = filterPrefs.getFavouritesForDb();
		if (favouritesValue >= 0)
		{
			whereClause = whereClause + "favourite=" + favouritesValue + "";
			whereClauseRequired = true;
		}
		
		if (!whereClauseRequired)
		{
			whereClause = "";
		}
		return whereClause;
	}
	*/
	
	//	Helper functions for converting data to and from SQL friendly types
	public String dateToString(Date dateData)
	{
		String dateForInsert = dateData.toString();
		return dateForInsert;
	}
	
	public Date stringToDate(String dateString)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK);

		Date dateData = new Date();
		try {
			dateData = dateFormat.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateData;
	}

	public int boolToInt(boolean boolValue)
	{
		int intValue = (boolValue) ? 1 : 0;
		return intValue;
	}
	
	public boolean intToBool(int intValue)
	{
		boolean boolValue = (0 == intValue) ? false : true;
		return boolValue;
	}
	/*
	//	From my friend stack overflow
//	Access methods
	public Cursor Query(String query, String ... params){
	    this.startReading();
	    Cursor c = mWritable.rawQuery(query, params);
	    return c;
	}

	public String GetScalar(String query, String ... params){
	    String res = null;
	    this.startReading();
	    Cursor c;
	    try {
	        c = Query(query, params);
	        if(c.moveToFirst()){
	            if(!c.isNull(0)){
	                res = c.getString(0);
	            }
	        }
	        c.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    this.stopReading();
	    return res;
	}

	public String GetScalar(String query){
	    return GetScalar(query, new String[0]);
	}

	public String[] GetColumn(String query, String ... params){
	    List<String> list = new ArrayList<String>();
	    this.startReading();
	    Cursor c;
	    try {
	        c = Query(query, params);
	        while(c.moveToNext()){
	            if(!c.isNull(0)){
	                list.add(c.getString(0));
	            }
	        }
	        c.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    this.stopReading();
	    return list.toArray(new String[0]);
	}

	public String[] GetColumn(String query){
	    return GetColumn(query, new String[0]);
	}
	
	//	Management methods
	public SQLiteDatabase getDatabase(){
	    if(mWritable == null)
	        mWritable = this.getWritableDatabase();
	    if(mWritable.isOpen())
	        return mWritable;
	    else
	        mWritable = this.getWritableDatabase();
	    return mWritable;
	}

	public void startReading(){
	    mWritable = getDatabase();
	}

	public void stopReading(){
	    if(mWritable != null)
	        if(mWritable.isOpen())
	            if(!mWritable.inTransaction())
	                mWritable.close();
	}

	public void end(){
	    if(mWritable == null)
	        return;
	    if(mWritable.isOpen()){
	        if(mWritable.inTransaction())
	            mWritable.endTransaction();
	        mWritable.close();
	    }
	}
*/

}
