package com.example.foodbookbd;

import java.io.IOException;

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

import android.os.AsyncTask;
import android.util.Log;

public class FetchResturantInfo extends AsyncTask<Void, Void, Void> {

	public AsyncResponse delegate = null;

	@Override
	protected Void doInBackground(Void... params) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet getResponse = new HttpGet(
				"http://dimik.webege.com/foodbookdhaka/restaurent_info_get.php");

		try {
			HttpResponse response = client.execute(getResponse);
			int status = response.getStatusLine().getStatusCode();

			if (status == HttpStatus.SC_OK) {
				Log.d("Started and connected", "done");

				HttpEntity entity = response.getEntity();
				String jsonstr = EntityUtils.toString(entity);
				JSONArray jsonArray;
				Log.v("data", jsonstr);
				long id;
				String Name, Adress;
				double Latitude, Longitude, rank;
				try {

					jsonArray = new JSONArray(jsonstr);

					for (int i =0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						//id = jsonObject.getLong(DBHelperRestaurent.Id);
						//Log.d("id of" + id, id + "");
						//id=jsonObject.getLong(DBHelperRestaurent.Id);
						 id=jsonObject.getInt("Id");
						Name = (String) jsonObject.get("Name");
						Adress = (String) jsonObject.get("Address");
						Latitude = Double.valueOf((String) jsonObject
								.get("Latitude"));
						Longitude = Double.valueOf((String) jsonObject
								.get("Longitude"));
						rank = Double.valueOf((String) jsonObject.get("Rank"));
						RestaurentInfo restInfo = new RestaurentInfo(id, Name,
								Adress, Longitude, Latitude, rank);
						WelcomeActivity.restInfoList.add(restInfo);
					}
					Log.v("size", "" + WelcomeActivity.restInfoList.size());

				} catch (JSONException e) {

					e.printStackTrace();
				}

			} else {

			}
		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		delegate.dataFetchComplete(true);

	}

}
