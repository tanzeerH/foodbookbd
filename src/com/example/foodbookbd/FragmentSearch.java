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
import org.xml.sax.DTDHandler;

import com.google.android.gms.internal.bt;

import android.R.string;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FragmentSearch extends Fragment implements
		android.view.View.OnClickListener, OnItemSelectedListener,OnItemClickListener {

	ListView listView;
	ArrayList<RestaurentInfo> searchRestResult=new ArrayList<RestaurentInfo>();
	ArrayList<FoodItem> searchFoodResult;
	RestaurentDetailsFragment restaurentDetailsFragment;
	CLVAdapter adapter;
	Spinner spinnerOption;
	Button buttonsearch;
	String[] optionFood = { "Burger", "Biryani", "Pizza" };
	ArrayAdapter<String> spinnerOptionsFoodAdapter;
	String searchName;
	int choice;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_search, null, false);

		listView = (ListView) view.findViewById(R.id.search_result_list);
		
		spinnerOption = (Spinner) view.findViewById(R.id.sp_options);
		spinnerOptionsFoodAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, optionFood);
		spinnerOption.setAdapter(spinnerOptionsFoodAdapter);
		spinnerOption.setOnItemSelectedListener(this);
		buttonsearch = (Button) view.findViewById(R.id.btn_search_name);
		buttonsearch.setOnClickListener(this);

		return view;
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.btn_search_name) {

			String choice = spinnerOption.getSelectedItem().toString();
			new FetchSearch(choice).execute();
			Toast.makeText(getActivity(), choice, Toast.LENGTH_LONG).show();

		}

	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view,
			int position, long id) {

		// TODO Auto-generated method stub
		/*
		 * if (position == 0) {
		 * spinnerOption.setAdapter(spinnerOptionsRestAdapter); } else if
		 * (position == 1) {
		 * spinnerOption.setAdapter(spinnerOptionsFoodAdapter); }
		 */
	}
	public void setAdapter()
	{
		adapter = new CLVAdapter(getActivity().getApplicationContext(),
		R.layout.clv_row,searchRestResult);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		
			restaurentDetailsFragment=new RestaurentDetailsFragment();
			restaurentDetailsFragment.restaurent=searchRestResult.get(position);
			android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager()
					.beginTransaction();
			transaction.addToBackStack(null);
			transaction.hide(this);
			transaction.replace(android.R.id.content,restaurentDetailsFragment);
			transaction.commit();

		

	}
	/*
	 * @Override public void onItemClick(AdapterView<?> arg0, View arg1, int
	 * position, long id) { // TODO Auto-generated method stub Intent intent =
	 * new Intent(getActivity().getApplicationContext(),
	 * RestaurentDetails.class); intent.putExtra(DBHelperRestaurent.Id,
	 * searchRestResult.get(position) .getId());
	 * intent.putExtra(DBHelperRestaurent.Name, searchRestResult.get(position)
	 * .getName()); intent.putExtra(DBHelperRestaurent.Address,
	 * searchRestResult.get(position).getAddress());
	 * intent.putExtra(DBHelperRestaurent.Latitude,
	 * searchRestResult.get(position).getLatitude());
	 * intent.putExtra(DBHelperRestaurent.Longitude,
	 * searchRestResult.get(position).getLongitude());
	 * intent.putExtra(DBHelperRestaurent.Rank, searchRestResult.get(position)
	 * .getRank()); startActivity(intent);
	 * 
	 * }
	 */
	private class FetchSearch extends AsyncTask<Void, Void, Void> {
		InputStream IS;
		String msg="";
		JSONArray jsonArray;
		String foodname;

		public FetchSearch(String str) {
			this.foodname = str;
		}

		@Override
		protected Void doInBackground(Void... params) {
			String url = "http://dimik.webege.com/foodbookdhaka/get_search_result.php";
			HttpClient clinet = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			ArrayList<NameValuePair> key = new ArrayList<NameValuePair>();
			key.add(new BasicNameValuePair("json", "" + foodname));

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
			msg = msg.substring(0, msg.length() - 146);
			try {
				jsonArray = new JSONArray(msg);

				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObject = jsonArray.getJSONObject(i);
					String Name = (String) jsonObject.get("Name");
					String Adress = (String) jsonObject.get("Address");
					double Latitude = Double.valueOf((String) jsonObject
							.get("Latitude"));
					double Longitude = Double.valueOf((String) jsonObject
							.get("Longitude"));
					double rank = Double.valueOf((String) jsonObject.get("Rank"));
					long id=jsonObject.getInt("Id");
					RestaurentInfo restInfo = new RestaurentInfo(id, Name,
							Adress, Longitude, Latitude, rank);
					
					searchRestResult.add(restInfo);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
				Log.v("msg", msg);
			Toast.makeText(getActivity(),"Complete"+searchRestResult.size()+msg, Toast.LENGTH_LONG).show();

			setAdapter();
			super.onPostExecute(result);
		}

	}

	

}
