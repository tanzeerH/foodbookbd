package com.example.foodbookbd;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseAdapter {
	SQLiteDatabase database;
	DBHelper dbhelper;
	
	public DataBaseAdapter(Context context) {
		dbhelper=new DBHelper(context);
	}
	
	
	public void open()
	{
		database=dbhelper.getReadableDatabase();
	}
	
	public void close()
	{
		database.close();
	}
	
	public long insert(RestaurentInfo restInfo) 
	{
		ContentValues values = new ContentValues();
		values.put("Name", restInfo.getName());
		values.put("Address", restInfo.getAddress());
		values.put("Latitude", restInfo.getLatitude());
		values.put("Longitude", restInfo.getLongitude());
		values.put("Rank", restInfo.getRank());
		
		return database.insert(DBHelper.TableName, "", values);
	}

}

