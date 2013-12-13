package com.example.foodbookbd;

import java.util.ArrayList;

import org.xml.sax.DTDHandler;

import android.R.string;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;

public class FragmentSearch extends Fragment implements
		android.view.View.OnClickListener /*, OnItemClickListener*/{

	View view;
	ListView listView;
	ArrayList<RestaurentInfo> searchRestResult;
	ArrayList<FoodItem> searchFoodResult;
	CLVAdapter restAdapter;
	CLVAdapterMenu menuAdapter;
	Cursor cursor;
	

	Spinner spinner;
	EditText editText;
	Button button;
	String[] columnsRestaurent = { DBHelperRestaurent.Id,
			DBHelperRestaurent.Name, DBHelperRestaurent.Address,
			DBHelperRestaurent.Latitude, DBHelperRestaurent.Longitude,
			DBHelperRestaurent.Rank };

	String[] columnsFood = { DBHelperMenu.MenuRestId, DBHelperMenu.MenuName,
			DBHelperMenu.MenuPrice };

	String[] spinnerlist = { "Restaurent", "Food" };
	ArrayAdapter<String> spinnerAdapter;
	String searchName;
	int choice;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_search, null, false);

		listView = (ListView) view.findViewById(R.id.search_result_list);
		searchRestResult = new ArrayList<RestaurentInfo>();
		searchFoodResult= new ArrayList<FoodItem>();
		restAdapter= new CLVAdapter(getActivity().getApplicationContext(),
				R.layout.clv_row, searchRestResult);
		menuAdapter= new CLVAdapterMenu(getActivity()
				.getApplicationContext(), R.layout.clv_row, searchFoodResult);
		listView.setAdapter(restAdapter);

		spinner = (Spinner) view.findViewById(R.id.search_catagory_spinner);
		spinnerAdapter = new ArrayAdapter<String>(getActivity()
				.getApplicationContext(),
				android.R.layout.simple_spinner_dropdown_item, spinnerlist);
		spinner.setAdapter(spinnerAdapter);

		editText = (EditText) view.findViewById(R.id.search_name_edittext);
		button = (Button) view.findViewById(R.id.btn_search_name);
		button.setOnClickListener(this);

		return view;
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.btn_search_name) {
			searchName = editText.getText().toString();
			choice = spinner.getSelectedItemPosition();
			button.setClickable(false);
			new ResultListLoaderThread().start();
		}

	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				
				Log.d("handler", "started");
				if(choice==0)
				{
					Log.d("handler", "choice 0");
					listView.setAdapter(restAdapter);
				}
				else if(choice==1)
				{
					Log.d("handler", "choice 1");
					listView.setAdapter(menuAdapter);
				}
				
				button.setClickable(true);
			}
		}
	};
	
	class ResultListLoaderThread extends Thread {

		@Override
		public void run() {
			// TODO Auto-generated method stub

			if (choice == 0) 
			{
				
								
				String selection = DBHelperRestaurent.Name + " LIKE '%" + searchName + "%'";
				MainActivity.databaseAdapter.open();
				cursor = MainActivity.databaseAdapter.database.query(
						DBHelperRestaurent.TableName, columnsRestaurent,
						selection, null, null, null, null);
				Log.d("cursor made", "done");
				RestaurentInfo restInfo;
				searchRestResult.clear();
				cursor.moveToFirst();
				while (!cursor.isAfterLast()) 
				{
					restInfo = new RestaurentInfo();
					restInfo.setId(cursor.getLong(0));
					restInfo.setName(cursor.getString(1));
					restInfo.setAddress(cursor.getString(2));
					restInfo.setLatitude(cursor.getFloat(3));
					restInfo.setLongitude(cursor.getFloat(4));
					restInfo.setRank(cursor.getFloat(5));

					Log.d("searching", restInfo.toString());

					searchRestResult.add(restInfo);
					handler.sendEmptyMessage(1);

					cursor.moveToNext();
					MainActivity.databaseAdapter.close();

				}
				
			} 
			else if (choice == 1)
			{
				String selection = DBHelperMenu.MenuName + " LIKE '%" + searchName + "%'";
				DataBaseAdapterMenu menudbAdapter = new DataBaseAdapterMenu(
						getActivity().getApplicationContext());
				menudbAdapter.open();
				SQLiteDatabase database = menudbAdapter.getDatabase();
				cursor = database.query(DBHelperMenu.TableNameMenu,
						columnsFood, selection, null, null, null, null);
				
				Log.d("cursor is null",  (cursor==null)+"");
				
				FoodItem foodItem;
				searchFoodResult.clear();
				
				cursor.moveToFirst();
				while (!cursor.isAfterLast()) 
				{
					foodItem = new FoodItem();
					foodItem.setRest_Id(cursor.getLong(0));
					foodItem.setName(cursor.getString(1));
					foodItem.setPrice(cursor.getInt(2));
					
					Log.d("New favourite", foodItem.toString());

					searchFoodResult.add(foodItem);
					handler.sendEmptyMessage(1);

					cursor.moveToNext();

				
					menudbAdapter.close();
				}

			}
		}

	}

	/*@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getActivity().getApplicationContext(),
				RestaurentDetails.class);
		intent.putExtra(DBHelperRestaurent.Id, searchRestResult.get(position)
				.getId());
		intent.putExtra(DBHelperRestaurent.Name, searchRestResult.get(position)
				.getName());
		intent.putExtra(DBHelperRestaurent.Address,
				searchRestResult.get(position).getAddress());
		intent.putExtra(DBHelperRestaurent.Latitude,
				searchRestResult.get(position).getLatitude());
		intent.putExtra(DBHelperRestaurent.Longitude,
				searchRestResult.get(position).getLongitude());
		intent.putExtra(DBHelperRestaurent.Rank, searchRestResult.get(position)
				.getRank());
		startActivity(intent);

	}*/
}
