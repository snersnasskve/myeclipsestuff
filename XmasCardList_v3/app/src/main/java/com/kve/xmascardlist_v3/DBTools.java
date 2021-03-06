package com.kve.xmascardlist_v3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by PC on 27/11/2017.
 */

public class DBTools extends SQLiteOpenHelper {
    private SQLiteDatabase mWritable;

    private static String kCONTACT_ID_MAP = "contactId";
    private static String kFIRSTNAME_MAP = "firstName";
    private static String kXMASSENT_MAP = "xmasSent";

    public DBTools(Context appContext) {
        super(appContext, "xmascardlist.db", null, 8);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        //	Err on the side of too many fields - can always auto fill
        String query = "CREATE TABLE contacts ( contact_id INTEGER PRIMARY KEY AUTOINCREMENT, first_name TEXT, last_name TEXT, phone_number TEXT, email_address TEXT, home_address TEXT, area_code TEXT, country TEXT, xmas_rec TEXT, xmas_sent INTEGER, xmas_email INTEGER, last_sent TEXT, favourite INTEGER, kids TEXT)" ;
        db.execSQL(query);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        switch (newVersion){
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:

                db.execSQL("ALTER TABLE contacts ADD kids TEXT");
        }
        //String query = "DROP TABLE IF EXISTS contacts";
        //db.execSQL(query);
        //onCreate(db);
    }



    public void insertContact(HashMap<String, Object> contactValues)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("first_name", 	(String) contactValues.get(kFIRSTNAME_MAP));
        values.put("last_name", 	(String) contactValues.get("lastName"));
        values.put("phone_number", 	(String) contactValues.get("phone"));
        values.put("email_address",	(String) contactValues.get("email"));
        values.put("home_address", 	(String) contactValues.get("address"));
        values.put("area_code", 	(String) contactValues.get("areaCode"));
        values.put("country", 		(String) contactValues.get("country"));
        values.put("xmas_rec", 		(String) contactValues.get("xmasRec"));
        values.put("xmas_sent", 	(Integer)contactValues.get(kXMASSENT_MAP));
        values.put("xmas_email", 	(Integer)contactValues.get("xmasEmail"));
        values.put("last_sent", 	(String) contactValues.get("lastSent"));
        values.put("favourite", 	(Integer)contactValues.get("favourite"));
        if ((String)contactValues.get("kids") == null)
        {
            values.put("kids", 	"");
        }
        else
        {
            values.put("kids", 	(String) contactValues.get("kids"));
        }

        db.insert("contacts", null, values);
        db.close();
    }

    public int updateContact(HashMap<String, Object> contactValues)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("first_name", 	(String) contactValues.get(kFIRSTNAME_MAP));
        values.put("last_name", 	(String) contactValues.get("lastName"));
        values.put("phone_number", 	(String) contactValues.get("phone"));
        values.put("email_address",	(String) contactValues.get("email"));
        values.put("home_address", 	(String) contactValues.get("address"));
        values.put("area_code", 	(String) contactValues.get("areaCode"));
        values.put("country", 		(String) contactValues.get("country"));
        values.put("xmas_rec", 		(String)contactValues.get("xmasRec"));
        values.put("xmas_sent", 	(Integer)contactValues.get(kXMASSENT_MAP));
        values.put("xmas_email", 	(Integer)contactValues.get("xmasEmail"));
        values.put("last_sent", 	(String) contactValues.get("lastSent"));
        values.put("favourite", 	(Integer)contactValues.get("favourite"));
        if ((String)contactValues.get("kids") == null)
        {
            values.put("kids", 	"");
        }
        else
        {
            values.put("kids", 	(String) contactValues.get("kids"));
        }
        return db.update("contacts", values,
                "contact_id = ?", new String[] {(String)contactValues.get(kCONTACT_ID_MAP)});

    }

    public void deleteContact(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM contacts WHERE contact_id='" + id + "'";
        db.execSQL(deleteQuery);
    }


    public HashMap<String, Object> getContact(String id)
    {
        HashMap<String, Object> contactMap = new HashMap<String, Object>();

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM contacts WHERE contact_id=?" ;
        String params[] = {id};
        //Cursor cursor = Query(selectQuery, params);
        Cursor cursor = db.rawQuery(selectQuery, params);

        if (cursor.moveToFirst()){
            contactMap.put(kCONTACT_ID_MAP, cursor.getLong(0));
            contactMap.put(kFIRSTNAME_MAP, cursor.getString(1));
            contactMap.put("lastName", 	cursor.getString(2));
            contactMap.put("phone", 	cursor.getString(3));
            contactMap.put("email", 	cursor.getString(4));
            contactMap.put("address", 	cursor.getString(5));
            contactMap.put("areaCode", 	cursor.getString(6));
            contactMap.put("country", 	cursor.getString(7));
            contactMap.put("xmasRec", 	cursor.getString(8));
            contactMap.put(kXMASSENT_MAP, 	cursor.getInt(9));
            contactMap.put("xmasEmail",	cursor.getInt(10));
            contactMap.put("lastSent",	cursor.getString(11));
            contactMap.put("favourite",	cursor.getInt(12));
            if (cursor.getColumnCount() > 13)
            {
                contactMap.put("kids",	cursor.getString(13));
            }
        }
        cursor.close();
        return contactMap;
    }

    //	Get filtered or unfiltered list
    public ArrayList<HashMap<String, Object>> getContacts()
    {
        ArrayList<HashMap<String, Object>> contactArray = new ArrayList<HashMap<String, Object>>();

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM contacts ORDER BY first_name";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                HashMap<String, Object> contactMap = new HashMap<String, Object>();
                contactMap.put(kCONTACT_ID_MAP,	cursor.getLong(0));
                contactMap.put(kFIRSTNAME_MAP,	cursor.getString(1));
                contactMap.put("lastName", 	cursor.getString(2));
                contactMap.put("phone", 	cursor.getString(3));
                contactMap.put("email", 	cursor.getString(4));
                contactMap.put("address", 	cursor.getString(5));
                contactMap.put("areaCode", 	cursor.getString(6));
                contactMap.put("country", 	cursor.getString(7));
                contactMap.put("xmasRec", 	cursor.getString(8));
                contactMap.put(kXMASSENT_MAP, 	cursor.getInt(9));
                contactMap.put("xmasEmail",	cursor.getInt(10));
                contactMap.put("lastSent",	cursor.getString(11));
                contactMap.put("favourite",	cursor.getInt(12));
                if (cursor.getColumnCount() > 13)
                {
                    contactMap.put("kids",	cursor.getString(13));
                }
                contactArray.add(contactMap);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contactArray;
    }

    public ArrayList<HashMap<String, Object>> getContacts(FilterPrefs filterPrefs)
    {
        ArrayList<HashMap<String, Object>> contactArray = new ArrayList<HashMap<String, Object>>();

        SQLiteDatabase db = this.getReadableDatabase();
        String whereClause = getWhereClause(filterPrefs);

        String selectQuery = "SELECT * FROM contacts " + whereClause + " ORDER BY first_name" ;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                HashMap<String, Object> contactMap = new HashMap<String, Object>();
                contactMap.put(kCONTACT_ID_MAP,	cursor.getLong(0));
                contactMap.put(kFIRSTNAME_MAP,	cursor.getString(1));
                contactMap.put("lastName", 	cursor.getString(2));
                contactMap.put("phone", 	cursor.getString(3));
                contactMap.put("email", 	cursor.getString(4));
                contactMap.put("address", 	cursor.getString(5));
                contactMap.put("areaCode", 	cursor.getString(6));
                contactMap.put("country", 	cursor.getString(7));
                contactMap.put("xmasRec", 	cursor.getString(8));
                contactMap.put(kXMASSENT_MAP, 	cursor.getInt(9));
                contactMap.put("xmasEmail",	cursor.getInt(10));
                contactMap.put("lastSent",	cursor.getString(11));
                contactMap.put("favourite",	cursor.getInt(12));
                if (cursor.getColumnCount() > 13)
                {
                    contactMap.put("kids",	cursor.getString(13));
                }
                contactArray.add(contactMap);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contactArray;
    }

    private String getWhereClause(FilterPrefs filterPrefs)
    {
        String whereClause = "";
        boolean whereClauseRequired = false;

        List< String> whereClauses = new ArrayList <String>();

        //	Favourites
        int favouritesValue = filterPrefs.getFavouritesForDb();
        if (favouritesValue >= 0)
        {
            //whereClause = whereClause + "favourite=" + favouritesValue + "";
            whereClauses.add("favourite=" + favouritesValue + " ");
            whereClauseRequired = true;
        }

        //	Region / Country
        List <String> countries = filterPrefs.getCountriesForDb();
        if (countries.size() > 0 && !countries.get(0).equals("All"))
        {
            String countriesString = TextUtils.join("' ,'", countries);
            countriesString = "('" + countriesString + "')";

            //now inClause will look like  "(23,343,33,55,43)" so use it to construct your SELECT
            //String select = "select * from table_name where id in " + inClause;
            whereClauses.add("country in " + countriesString + " ");
            whereClauseRequired = true;
        }

        //	Xmas Sent
        FilterPrefs.XmasSentType sent = filterPrefs.getXmasSentForDb();
        if (FilterPrefs.XmasSentType.ALL != sent)
        {
            whereClauseRequired = true;
            if (FilterPrefs.XmasSentType.SENT == sent)
            {
                whereClauses.add("xmas_sent = 1 ");
            }
            else if (FilterPrefs.XmasSentType.NOT_SENT == sent)
            {
                whereClauses.add("xmas_sent = 0 ");
            }
        }

        /////
        if (whereClauseRequired)
        {
            whereClause = " WHERE " +
                    TextUtils.join(" AND ", whereClauses);
        }
        return whereClause;
    }

    public void markAllXmasNotSent()
    {
        ArrayList<HashMap<String, Object>> contacts = this.getContacts();
        for  (HashMap<String, Object>  contactMap : contacts)
        {
            contactMap.put(kXMASSENT_MAP, 0);
            contactMap.put(kCONTACT_ID_MAP, (contactMap.get(kCONTACT_ID_MAP)).toString() );
            updateContact(contactMap);
        }
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
