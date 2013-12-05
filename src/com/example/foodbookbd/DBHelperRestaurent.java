package com.example.foodbookbd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperRestaurent extends SQLiteOpenHelper {
	
	public final static String DBName="FoodBookBd.db";
	public static final String RestaurentType="Restaurent";
	
	public static final String TableName="Restaurents";
	public static final String Id="Id";
	public static final String Name="Name";
	public static final String Address="Address";
	public static final String Latitude="Latitude";
	public static final String Longitude="Longitude";
	public static final String Rank="Rank";
	public static final String Favourited="Favourited";
	
	
	
	public static final String Table_Rest_SQl="CREATE TABLE "+ TableName +
			"( "+Id+" INTEGER AUTO_INCREMENT PRIMARY KEY,"+
			" "+Name+" VARCHAR(100),"+
			" "+Address+" VARCHAR(100),"+
			" "+Latitude+" float,"+
			" "+Longitude+" float,"+
			" "+Rank+" FLOAT,"+
			" "+Favourited+" VARCHAR(7)" +
					");";		
	

	public DBHelperRestaurent(Context context) {
		super(context, DBName, null, 1);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		database.execSQL(Table_Rest_SQl);

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	

}
