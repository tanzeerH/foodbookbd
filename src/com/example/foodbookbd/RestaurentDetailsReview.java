package com.example.foodbookbd;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.RSTextureView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;

public class RestaurentDetailsReview extends SherlockActivity implements OnClickListener{

	ListView lvReview;
	String[] columns = { DBHelperReview.Id, DBHelperReview.RestId,
			DBHelperReview.ReviewerName, DBHelperReview.ReviewDetails,
			DBHelperReview.Rate };

	Cursor cursor;
	CLVAdapterReview adapter;
	public static ArrayList<ReviewItem> reviewList;
	Button button;
	long rest_idToGetReview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_restaurent_review);

		Intent intent=getIntent();
		rest_idToGetReview=(Long) intent.getSerializableExtra(DBHelperRestaurent.Id);
		
		lvReview = (ListView) findViewById(R.id.review_list);
		reviewList = new ArrayList<ReviewItem>();
		adapter = new CLVAdapterReview(getApplicationContext(),
				R.layout.restaurent_review, reviewList);
		lvReview.setAdapter(adapter);
		
		button=(Button)findViewById(R.id.btn_add_new_review);
		button.setOnClickListener(this);
		
		new ReviewLoaderThread().start();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		lvReview.setAdapter(adapter);
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		lvReview.setAdapter(adapter);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(getApplicationContext(), GetReview.class);
		intent.putExtra(DBHelperReview.RestId, rest_idToGetReview);
		startActivity(intent);
	}
	
	class ReviewLoaderThread extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub

			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet getResponse = new HttpGet(
					"http://dimik.webege.com/foodbookdhaka/restaurent_review_get.php");

			DataBaseAdapterReview dbAdapter = new DataBaseAdapterReview(
					getApplicationContext());

			try {
				HttpResponse response = client.execute(getResponse);
				int status = response.getStatusLine().getStatusCode();

				if (status == HttpStatus.SC_OK) {
					Log.d("Started and connected", "done");

					HttpEntity entity = response.getEntity();
					String jsonstr = EntityUtils.toString(entity);
					JSONArray jsonArray;
					String reviewerName, reviewDetails;
					long id, rest_id, rank;
					try {

						jsonArray = new JSONArray(jsonstr);

						dbAdapter.open();
						dbAdapter.createTable();

						int rowNum = dbAdapter.getCount();
						Log.d("existing row number", rowNum + "");

						for (int i = rowNum; i < jsonArray.length(); i++) {
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							id = jsonObject.getLong(DBHelperReview.Id);
							Log.d("id of" + id, id + "");
							rest_id = jsonObject.getLong(DBHelperReview.RestId);
							reviewerName = jsonObject
									.getString(DBHelperReview.ReviewerName);
							reviewDetails = jsonObject
									.getString(DBHelperReview.ReviewDetails);
							rank = jsonObject.getLong(DBHelperReview.Rate);
							ReviewItem reviewItem = new ReviewItem(id, rest_id,
									rank, reviewerName, reviewDetails);

							dbAdapter.insert(reviewItem);
							Log.d("new restaurent", reviewItem.toString());
						}

						cursor = dbAdapter.database.query(
								DBHelperReview.TableNameReview, columns, DBHelperReview.RestId+ " = "+ rest_idToGetReview ,
								null, null, null, null);
						cursor.moveToFirst();
						while(!cursor.isAfterLast())
						{
							ReviewItem reviewItem=new ReviewItem();
							reviewItem.reviewId=cursor.getLong(0);
							reviewItem.restId=cursor.getLong(1);
							reviewItem.reviewerName=cursor.getString(2);
							reviewItem.reviewDetails=cursor.getString(3);
							reviewItem.rank=cursor.getLong(4);
							
							reviewList.add(reviewItem);
							cursor.moveToNext();
						}

						dbAdapter.close();
						handler.sendEmptyMessage(1);

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {

				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				lvReview.setAdapter(adapter);
			}

		};
	};
	
}
