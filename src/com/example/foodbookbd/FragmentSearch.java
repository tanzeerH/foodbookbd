package com.example.foodbookbd;

import java.util.ArrayList;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class FragmentSearch extends Fragment {

	View view;
	ListView listView;
	ArrayList<RestaurentInfo> searchResult;
	CLVAdapter adapter;
	Cursor cursor;
	RestaurentInfo restInfo;

	Spinner spinner;
	EditText editText;
	
	String[] columns = { DBHelperRestaurent.Id, DBHelperRestaurent.Name, DBHelperRestaurent.Address,
			DBHelperRestaurent.Latitude, DBHelperRestaurent.Longitude, DBHelperRestaurent.Rank };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_search, null, false);

		listView = (ListView) view.findViewById(R.id.search_result_list);
		searchResult = new ArrayList<RestaurentInfo>();
		adapter = new CLVAdapter(getActivity().getApplicationContext(),
				R.layout.clv_row, searchResult);
		listView.setAdapter(adapter);
		
		spinner=(Spinner)view.findViewById(R.id.search_catagory_spinner);
		editText=(EditText)view.findViewById(R.id.search_name_edittext);
		
		cursor = MainActivity.databaseAdapter.database.query(
				DBHelperRestaurent.TableName, columns, null, null, null, null, null);

		return view;
	}
	
	class ResultListLoaderThread extends Thread {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			String[] columns = { DBHelperRestaurent.Id, DBHelperRestaurent.Name, DBHelperRestaurent.Address,
					DBHelperRestaurent.Latitude, DBHelperRestaurent.Longitude, DBHelperRestaurent.Rank };
			String selection=DBHelperRestaurent.Favourited + "=" + "'true'";
			MainActivity.databaseAdapter.open();
			cursor = MainActivity.databaseAdapter.database.query(
					DBHelperRestaurent.TableName, columns, selection, null,
					null, null, null);
			
			
			cursor.moveToFirst();
			while(!cursor.isAfterLast())
			{
				restInfo=new  RestaurentInfo();
				restInfo.setId(cursor.getLong(0));
				restInfo.setName(cursor.getString(1));
				restInfo.setAddress(cursor.getString(2));
				restInfo.setLatitude(cursor.getFloat(3));
				restInfo.setLongitude(cursor.getFloat(4));
				restInfo.setRank(cursor.getFloat(5));
				
				Log.d("New favourite", restInfo.toString());
				
				searchResult.add(restInfo);
				handler.sendEmptyMessage(1);
				
				cursor.moveToNext();
				
			}
			MainActivity.databaseAdapter.close();
		}
	}
	
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1)
			{
				listView.setAdapter(adapter);
			}
		}
	};

}
