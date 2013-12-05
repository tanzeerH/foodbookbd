package com.example.foodbookbd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataBaseAdapterRestaurent {
	SQLiteDatabase database;
	DBHelperRestaurent dbhelper;

	public DataBaseAdapterRestaurent(Context context) {
		dbhelper = new DBHelperRestaurent(context);
	}

	public void open() {
		database = dbhelper.getReadableDatabase();
	}

	public void close() {
		database.close();
	}

	public long insert(RestaurentInfo restInfo) {
		ContentValues values = new ContentValues();
		values.put("Id", restInfo.getId());
		values.put("Name", restInfo.getName());
		values.put("Address", restInfo.getAddress());
		values.put("Latitude", restInfo.getLatitude());
		values.put("Longitude", restInfo.getLongitude());
		values.put("Rank", restInfo.getRank());

		return database.insert(DBHelperRestaurent.TableName, "", values);
	}

	public int getCount() {
		String[] columns = { DBHelperRestaurent.Id };

		Cursor cursor = database.query(DBHelperRestaurent.TableName, columns, null, null,
				null, null, null);
		return cursor.getCount();

	}

	public void updateRow(long id, String[] columns, String[] values) {

		String sqlquery = "UPDATE " + dbhelper.TableName + " SET ";
		
		for (int i = 0; i < columns.length; i++) 
		{
			sqlquery+= columns[i] + "= '" + values[i] + "'";
		}
		sqlquery+=" WHERE " + dbhelper.Id + "="+ id;
		Log.d("sqlquery", sqlquery);
		open();
		database.execSQL(sqlquery);
		close();
	}

}
