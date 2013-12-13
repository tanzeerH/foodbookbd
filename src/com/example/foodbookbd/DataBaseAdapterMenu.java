package com.example.foodbookbd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataBaseAdapterMenu {
	SQLiteDatabase database;
	DBHelperMenu dbhelper;

	public DataBaseAdapterMenu(Context context) {
		Log.d("constructor in adapter", "done");
		dbhelper = new DBHelperMenu(context);
	}

	public void open() {
		database = dbhelper.getReadableDatabase();
	}

	public void close() {
		database.close();
	}
	
	public void createTable()
	{
		Log.d("table creation", "hoise");
		Log.d("TABLQ SQL", DBHelperMenu.Table_Menu_SQl);
		database.execSQL(DBHelperMenu.Table_Menu_SQl);
	}
	public long insert(FoodItem foodInfo) {
		ContentValues values = new ContentValues();
		values.put(DBHelperMenu.MenuRestId, foodInfo.getRest_Id());
		values.put(DBHelperMenu.MenuName, foodInfo.getName());
		values.put(DBHelperMenu.MenuPrice, foodInfo.getPrice());

		return database.insert(DBHelperMenu.TableNameMenu, "", values);
	}

	public int getCount() {
		String[] columns = { DBHelperMenu.MenuRestId };

		Cursor cursor = database.query(DBHelperMenu.TableNameMenu, columns, null, null,
				null, null, null);
		return cursor.getCount();

	}

	public void updateRow(long id, String[] columns, String[] values) {

		String sqlquery = "UPDATE " + dbhelper.MenuName + " SET ";
		
		for (int i = 0; i < columns.length; i++) 
		{
			sqlquery+= columns[i] + "= '" + values[i] + "'";
		}
		sqlquery+=" WHERE " + dbhelper.MenuRestId + "="+ id;
		Log.d("sqlquery", sqlquery);
		open();
		database.execSQL(sqlquery);
		close();
	}

	public SQLiteDatabase getDatabase() {
		
		return database;
	}

}
