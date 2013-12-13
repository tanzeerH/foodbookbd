package com.example.foodbookbd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelperReview extends SQLiteOpenHelper {
	
	public static final String DBName="FoodBookBd.db";
	
	public static final String TableNameReview="Review";
	
	public static final String Id="Id";
	public static final String RestId="Rest_id";
	public static final String ReviewerName="ReviewerName";
	public static final String ReviewDetails="ReviewDetails";
	public static final String Rate="Rate";
	
	public static final String Table_REVIEW_SQl="CREATE TABLE IF NOT EXISTS "+ TableNameReview +
			"( "+Id +" INTEGER AUTO_INCREMENT,"+
			" "+RestId+" INTEGER,"+
			" "+ReviewerName+" VARCHAR(100),"+
			" "+ReviewDetails+" VARCHAR(100),"+
			" "+Rate+" INTEGER"+
					");";	
	
		
	
	public DBHelperReview(Context context) {
		super(context, DBName, null, 1);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
	}


	@Override
	public void onCreate(SQLiteDatabase database) {
	
	}
	
}
