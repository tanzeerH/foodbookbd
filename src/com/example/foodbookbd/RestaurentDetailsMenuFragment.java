package com.example.foodbookbd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class RestaurentDetailsMenuFragment extends Fragment {
	
	public RestaurentInfo restaurentInfo=null;
	public ArrayList<FoodItem> foodItem=new ArrayList<FoodItem>();
	public ListView listview;
	public CLVAdapterMenu adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.details_restaurent_menu, null, false);
		listview=(ListView)view.findViewById(R.id.menu_food_list);
		new FetchMenu(restaurentInfo.getId()).execute();

		return view;
	}
	public void  setadpter()
	{
		adapter = new CLVAdapterMenu(getActivity().getApplicationContext(),
				R.layout.clv_row,foodItem);
				listview.setAdapter(adapter);
				//listview.setOnItemClickListener(this);
		
	}
	private class FetchMenu extends AsyncTask<Void, Void, Void> {
		long id;
		InputStream IS;
		String msg ="";
		JSONArray jsonArray;

		public FetchMenu(long id) {
			this.id = id;

		}

		@Override
		protected Void doInBackground(Void... params) {

			String url = "http://dimik.webege.com/foodbookdhaka/restaurent_menu_get_1.php";
			HttpClient clinet = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			ArrayList<NameValuePair> key = new ArrayList<NameValuePair>();
			key.add(new BasicNameValuePair("json",""+id));

			try {
				httppost.setEntity(new UrlEncodedFormEntity(key));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				HttpResponse response = clinet.execute(httppost);
				HttpEntity entity = response.getEntity();
				IS = entity.getContent();

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			BufferedReader bf = new BufferedReader(new InputStreamReader(IS));
			String line = "";
			
			try {
				while ((line = bf.readLine()) != null) {
					msg = msg + line;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			msg=msg.substring(0,msg.length()-146);
			try {
				jsonArray = new JSONArray(msg);

				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObject = jsonArray.getJSONObject(i);
					Long id = jsonObject.getLong("Rest_Id");
					Log.d("msg", msg);
					String Name = (String) jsonObject.get("Name");
					int Price;
					Price = jsonObject.getInt("Price");
					foodItem.add(new FoodItem(id,Name, Price));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if(jsonArray.length()==0)
				Toast.makeText(getActivity().getApplicationContext(),"No menu Available",Toast.LENGTH_LONG).show();
			else
			{
				Toast.makeText(getActivity().getApplicationContext(),msg+foodItem.size(),Toast.LENGTH_LONG).show();
				setadpter();
			}

				

		}

	}

}
