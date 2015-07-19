package com.kve.mapmanager;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBTools extends SQLiteOpenHelper {

	public DBTools(Context appContext) {
		super(appContext, "mapmanager", null, 1);
	// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//	Err on the side of too many fields - can always auto fill
		String recentQuery = "CREATE TABLE recent ( place_id INTEGER PRIMARY KEY, place_name TEXT )" ;
		db.execSQL(recentQuery);
		String favouritesQuery = "CREATE TABLE favourites ( place_id INTEGER PRIMARY KEY, place_name TEXT )" ;
		db.execSQL(favouritesQuery);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String recentQuery = "DROP TABLE IF EXISTS recent";
		db.execSQL(recentQuery);
		String favouritesQuery = "DROP TABLE IF EXISTS favourites";
		db.execSQL(favouritesQuery);
		onCreate(db);
	}
	
	public ArrayList<HashMap<String, Object>> getRecent()
	{
		ArrayList<HashMap<String, Object>> recentArray = new ArrayList<HashMap<String, Object>>();
		
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM recent";
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if (cursor.moveToFirst()){
			do {
				HashMap<String, Object> recentMap = new HashMap<String, Object>();
				recentMap.put("placeId",		cursor.getLong(0));
				recentMap.put("placeName",		cursor.getString(1));
				if (null !=  cursor.getString(1))
				{
				recentArray.add(recentMap);
				}
			} while (cursor.moveToNext());
		}
		return recentArray;
	}
	
	public ArrayList<HashMap<String, Object>> getFavourites()
	{
		ArrayList<HashMap<String, Object>> favouritesArray = new ArrayList<HashMap<String, Object>>();
		
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM favourites";
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if (cursor.moveToFirst()){
			do {
				HashMap<String, Object> favouritesMap = new HashMap<String, Object>();
				favouritesMap.put("placeId",		cursor.getLong(0));
				favouritesMap.put("placeName",		cursor.getString(1));
				favouritesArray.add(favouritesMap);
			} while (cursor.moveToNext());
		}
		return favouritesArray;
	}
	

	//	First we have to read em all, then move them all down one, and bin the excess
	public void insertRecent(String newPlaceName)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		HashMap<String, Object> newRecent = new HashMap<String, Object>();
		newRecent.put("placeName", newPlaceName);
		
		//	get all the recent
		ArrayList<HashMap<String, Object>> recentPlaces = getRecent();
		
		//	delete all the recent from the database
		if ( recentPlaces.size() > 0)
		{
			deleteAllRecent();
		}

		//	if there is the same place name, remove it from the array
		int existingRecentPos = -1;
		for (int placeCounter = 0 ; placeCounter < recentPlaces.size(); placeCounter++)
		{
			if (recentPlaces.get(placeCounter).get("placeName").equals(newPlaceName))
			{
				existingRecentPos = placeCounter;
				break;
			}
		}
		
		if (existingRecentPos > 0)
		{
			recentPlaces.remove(existingRecentPos);
		}
		
		if (0 != existingRecentPos)
		{
			recentPlaces.add(0, newRecent );
		}
		
			
		//	write the new array - making sure you don't exceed 10
		int placeCounter = 0;
		while (placeCounter < 10 && placeCounter < recentPlaces.size())
		{
			values.put("place_name", 	(String) recentPlaces.get(placeCounter).get("placeName"));
			db.insert("recent", null, values);
			placeCounter++;
		}
		db.close();
	}
	
	public void insertFavourite(HashMap<String, Object> favouriteValues)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("place_name", 	(String) favouriteValues.get("placeName"));
		db.insert("favourites", null, values);
		db.close();
	}
	

	//	Doubt we'll need this so not done for favourites
	public int updateRecent(HashMap<String, Object> recentValues)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("place_name", 	(String) recentValues.get("placeName"));
		return db.update("recent", values, 
				"place_id = ?", new String[] {(String)recentValues.get("contactId")});
		
	}

	public void deleteRecent(Long id)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		String deleteQuery = "DELETE FROM recent WHERE place_id = '" + id + "'";
		db.execSQL(deleteQuery);			
	}

	public void deleteAllRecent()
	{
		/*
		SQLiteDatabase db = this.getWritableDatabase();
		String deleteQuery = "DELETE FROM recent";
		db.execSQL(deleteQuery);	
		*/
		ArrayList<HashMap<String, Object>> toDelete = getRecent();
		for (HashMap<String, Object> recentInst : toDelete)
		{
			deleteRecent((Long) recentInst.get("placeId"));
		}
	}

	public void deleteFavourite(String id)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		String deleteQuery = "DELETE FROM favourites WHERE place_id = '" + id + "'";
		db.execSQL(deleteQuery);			
	}


	//	Doubt we'll need this so not done for favourites
	public HashMap<String, Object> getRecent(String id)
	{
		HashMap<String, Object> recentMap = new HashMap<String, Object>();
			
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM recent WHERE place_id = " + id + "";
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if (cursor.moveToFirst()){
			recentMap.put("placeId", cursor.getLong(0));
			recentMap.put("placeName", cursor.getString(1));
		}
		return recentMap;
	}

	
}
