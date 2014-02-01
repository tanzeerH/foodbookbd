package com.example.foodbookbd;

import java.util.ArrayList;

import com.google.android.gms.internal.cr;
import com.google.android.gms.internal.da;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelperRestaurent extends SQLiteOpenHelper {
	
	public final static String DBName="FoodBookBd1.db";
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
	public void insert(RestaurentInfo restInfo) {
		
		SQLiteDatabase database=this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("Id", restInfo.getId());
		values.put("Name", restInfo.getName());
		values.put("Address", restInfo.getAddress());
		values.put("Latitude", restInfo.getLatitude());
		values.put("Longitude", restInfo.getLongitude());
		values.put("Rank", restInfo.getRank());
		 database.insert(TableName, "", values);
		 
		 database.close();
		
	}
	public ArrayList<RestaurentInfo> getList()
	{
		ArrayList<RestaurentInfo> list=new ArrayList<RestaurentInfo>();
		
		String[] columns = { "Id","Name","Address","Latitude", "Longitude","Rank" };
		SQLiteDatabase db=this.getReadableDatabase();
		Log.v("msg","hello1");
		Cursor cursor = db.query(TableName, columns,null, null,
				null, null, null);
		Log.v("msg","hello2");
		while(cursor.moveToNext())
		{
			RestaurentInfo restinfo=new  RestaurentInfo();
			restinfo.setId(cursor.getLong(0));
			restinfo.setName(cursor.getString(1));
			restinfo.setAddress(cursor.getString(2));
			restinfo.setLatitude(cursor.getDouble(3));
			restinfo.setLongitude(cursor.getDouble(4));
			restinfo.setRank(cursor.getDouble(5));
			list.add(restinfo);
		}
		cursor.close();
		db.close();
		return list;
	}
	

}
