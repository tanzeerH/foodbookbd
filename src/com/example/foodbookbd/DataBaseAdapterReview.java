package com.example.foodbookbd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataBaseAdapterReview {
	SQLiteDatabase database;
	DBHelperReview dbhelper;

	public DataBaseAdapterReview(Context context) {
		Log.d("constructor in adapter", "done");
		dbhelper = new DBHelperReview(context);
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
		Log.d("TABLQ SQL", DBHelperReview.Table_REVIEW_SQl);
		database.execSQL(DBHelperReview.Table_REVIEW_SQl);
	}
	public long insert(ReviewItem reviewItem) {
		ContentValues values = new ContentValues();
		values.put(DBHelperReview.RestId, reviewItem.getRestId());
		values.put(DBHelperReview.ReviewerName, reviewItem.getReviewerName());
		values.put(DBHelperReview.ReviewDetails, reviewItem.getReviewDetails());
		values.put(DBHelperReview.Rate, reviewItem.getRank());
		
		return database.insert(DBHelperReview.TableNameReview, "", values);
	}

	public int getCount() {
		String[] columns = { DBHelperReview.Id };

		Cursor cursor = database.query(DBHelperReview.TableNameReview, columns, null, null,
				null, null, null);
		return cursor.getCount();

	}

	public void updateRow(long id, String[] columns, String[] values) {

		String sqlquery = "UPDATE " + dbhelper.ReviewerName + " SET ";
		
		for (int i = 0; i < columns.length; i++) 
		{
			sqlquery+= columns[i] + "= '" + values[i] + "'";
		}
		sqlquery+=" WHERE " + dbhelper.RestId + "="+ id;
		Log.d("sqlquery", sqlquery);
		open();
		database.execSQL(sqlquery);
		close();
	}

	public SQLiteDatabase getDatabase() {
		
		return database;
	}

}
