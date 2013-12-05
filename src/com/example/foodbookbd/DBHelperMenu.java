package com.example.foodbookbd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelperMenu extends SQLiteOpenHelper {
	
	public static final String DBName="FoodBookBd.db";
	public static final String MenuType="Menu";
	
	public static final String TableNameMenu="Fooditem";
	public static final String MenuRestId="Rest_Id";
	public static final String MenuName="Name";
	public static final String MenuPrice="Price";
	
	public static final String Table_Menu_SQl="CREATE TABLE IF NOT EXISTS "+ TableNameMenu +
			"( "+MenuRestId +" INTEGER ,"+
			" "+MenuName+" VARCHAR(100),"+
			" "+MenuPrice+" VARCHAR(100)"+
					");";	
	
	SQLiteDatabase database;
	
	
	public DBHelperMenu(Context context) {
		super(context, DBName, null, 1);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
	}


	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		this.database=database;
	}
	
}
