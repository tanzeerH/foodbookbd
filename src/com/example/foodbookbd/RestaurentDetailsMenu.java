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

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;

public class RestaurentDetailsMenu extends SherlockActivity {

	
	
	ListView lvMenu;
	ArrayList<FoodItem> menuItems;
	CLVAdapterMenu adapter;
	DataBaseAdapterMenu dbAdapter;
	long idOfRestaurent;
	ProgressDialog prog;
	String[] columns = { DBHelperMenu.MenuRestId, DBHelperMenu.MenuName,
			DBHelperMenu.MenuPrice };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_restaurent_menu);
		Intent intent = getIntent();
		idOfRestaurent = (Long) intent
				.getSerializableExtra(DBHelperRestaurent.Id);

		lvMenu = (ListView) findViewById(R.id.menu_food_list);
		menuItems = new ArrayList<FoodItem>();
		adapter = new CLVAdapterMenu(getApplicationContext(), R.layout.clv_row,
				menuItems);

		dbAdapter = new DataBaseAdapterMenu(getApplicationContext());
		dbAdapter.open();
		dbAdapter.createTable();
		dbAdapter.close();
		
		prog = new ProgressDialog(RestaurentDetailsMenu.this);
		prog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		prog.setIndeterminate(false);
 		
		prog.setTitle("Downloading");
		prog.setMessage("Please wait ...");       
 		prog.show();
		
		GetDataFromRemoteDatabase getData = new GetDataFromRemoteDatabase();
		getData.execute();
		
		
	
	}

	boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	class GetDataFromRemoteDatabase extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			
			
		}
		
		@Override
		protected Void doInBackground(Void... params) {

			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet getResponse = new HttpGet(
					"http://dimik.webege.com/foodbookdhaka/restaurent_menu_get.php");

			Log.d("async", "Running");
			try {
				HttpResponse response = client.execute(getResponse);
				int status = response.getStatusLine().getStatusCode();
				dbAdapter.open();

				if (status == HttpStatus.SC_OK) {
					Log.d("Started and connected", "done");

					HttpEntity entity = response.getEntity();
					String jsonstr = EntityUtils.toString(entity);
					JSONArray jsonArray;
					long id;
					String Name;
					int Price;
					try {

						jsonArray = new JSONArray(jsonstr);

						int rowNum = dbAdapter.getCount();
						Log.d("existing row number", rowNum + "");

						for (int i = rowNum; i < jsonArray.length(); i++) {
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							id = jsonObject.getLong(DBHelperMenu.MenuRestId);
							Log.d("id of" + id, id + "");
							Name = (String) jsonObject.get("Name");
							Price = jsonObject.getInt("Price");
							FoodItem foodInfo = new FoodItem(id, Name, Price);

							//menuItems.add(foodInfo);
							dbAdapter.insert(foodInfo);
							Log.d("new restaurent", foodInfo.toString());
						}

						// handler.sendEmptyMessage(1);

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
			Log.d("idofrest", idOfRestaurent+"");
			Cursor cursor = dbAdapter.database.query(
					DBHelperMenu.TableNameMenu, columns,
					DBHelperMenu.MenuRestId + " = " +idOfRestaurent,
					null, null, null,
					null);
			FoodItem foodItem;
			cursor.moveToFirst();
			Log.d("no food", "why");
			while(!cursor.isAfterLast())
			{
				foodItem=new FoodItem();

				foodItem.setRest_Id(cursor.getLong(0));
				foodItem.setName(cursor.getString(1));
				foodItem.setPrice(cursor.getInt(2));
				
				Log.d("food", foodItem.toString());
				menuItems.add(foodItem);
				cursor.moveToNext();
			}
			
			dbAdapter.close();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			Log.d("async", "Ended");
			//if(prog.isShowing())prog.dismiss();
			lvMenu.setAdapter(adapter);
		}
	}

	/*
	 * Handler handler=new Handler(){ public void
	 * handleMessage(android.os.Message msg) { if(msg.what==1) {
	 * adapter.notifyDataSetChanged(); }
	 * 
	 * }; };
	 */

}
