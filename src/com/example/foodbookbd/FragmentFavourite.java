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
import android.widget.ListView;

public class FragmentFavourite extends Fragment {
	ListView listView;
	CLVAdapter adapter;
	ArrayList<RestaurentInfo> favouritelist;
	RestaurentInfo restinfo;
	Cursor cursor ;
	View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.fragment_favourite, null, false);
		listView = (ListView) view.findViewById(R.id.favourite_restaurent_list);
		favouritelist = new ArrayList<RestaurentInfo>();
		adapter = new CLVAdapter(getActivity().getApplicationContext(), R.layout.clv_row,
				favouritelist);
		
		
		FavouriteListLoaderThread thread=new FavouriteListLoaderThread();
		thread.start();
		
		return view;
	}
	
	class FavouriteListLoaderThread extends Thread {

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
				restinfo=new  RestaurentInfo();
				restinfo.setId(cursor.getLong(0));
				restinfo.setName(cursor.getString(1));
				restinfo.setAddress(cursor.getString(2));
				restinfo.setLatitude(cursor.getFloat(3));
				restinfo.setLongitude(cursor.getFloat(4));
				restinfo.setRank(cursor.getFloat(5));
				
				Log.d("New favourite", restinfo.toString());
				
				favouritelist.add(restinfo);
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
