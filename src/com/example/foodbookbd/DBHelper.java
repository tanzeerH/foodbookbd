package com.example.foodbookbd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	
	public static final String DBName="FoodBookBd.db";
	
	public static final String TableName="Restaurents";
	public static final String Table_SQl="CREATE TABLE "+ TableName +
			"( Id INTEGER AUTO_INCREMENT PRIMARY KEY,"+
			" Name VARCHAR(100),"+
			" Address VARCHAR(100),"+
			" Latitude float,"+
			" Longitude float,"+
			" Rank FLOAT);";		
	
	public DBHelper(Context context) {
		super(context, DBName, null, 1);
		// TODO Auto-generated constructor stub
	}

	
	


	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		database.execSQL(Table_SQl);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	
	

}
