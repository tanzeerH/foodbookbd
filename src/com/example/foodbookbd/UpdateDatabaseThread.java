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

import android.R.string;
import android.os.Handler;
import android.util.Log;

public class UpdateDatabaseThread extends Thread {

	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet getResponse = new HttpGet(
				"http://10.0.2.2/FoodBookBd/restaurent_info_get.php");

		try {
			HttpResponse response = client.execute(getResponse);
			int status = response.getStatusLine().getStatusCode();
			
			if (status == HttpStatus.SC_OK) {
				Log.d("Started and connected", "done");

				HttpEntity entity = response.getEntity();
				String jsonstr = EntityUtils.toString(entity);
				JSONArray jsonArray;
				long id;
				String Name, Adress;
				float Latitude, Longitude,rank;
				try {
					
					jsonArray = new JSONArray(jsonstr);
					
					MainActivity.databaseAdapter.open();
					int rowNum=MainActivity.databaseAdapter.getCount();
					Log.d("existing row number", rowNum+"");
					
					for (int i = rowNum; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						id=jsonObject.getLong(DBHelperRestaurent.Id);
						Log.d("id of" + id, id+"");
						Name = (String) jsonObject.get("Name");
						Adress = (String) jsonObject.get("Address");
						Latitude = Float.valueOf((String) jsonObject.get("Latitude"));
						Longitude = Float.valueOf((String) jsonObject.get("Longitude"));
						rank=Float.valueOf((String)jsonObject.get("Rank"));
						RestaurentInfo restInfo = new RestaurentInfo(id,Name,
								Adress, Longitude, Latitude,rank);

						MainActivity.restInfoList.add(restInfo);
						MainActivity.databaseAdapter.insert(restInfo);
						Log.d("new restaurent", restInfo.toString());
					}
					
					//
					//FragmentRestaurent.adapter.notifyDataSetChanged();
					MainActivity.databaseAdapter.close();
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
	
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1)
			{
				FragmentRestaurent.adapter.notifyDataSetChanged();
			}
			
		};
	};

}
