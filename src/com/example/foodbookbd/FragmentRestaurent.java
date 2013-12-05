package com.example.foodbookbd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FragmentRestaurent extends Fragment implements OnItemClickListener {

	public static CLVAdapter adapter;
	ListView Menu;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_restaurent, null, false);
		adapter = new CLVAdapter(getActivity().getApplicationContext(),
				R.layout.clv_row, MainActivity.restInfoList);
		Menu = (ListView) view.findViewById(R.id.restaurent_list);
		Menu.setAdapter(adapter);
		Menu.setOnItemClickListener(this);
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		// TODO Auto-generated method stub

		Intent intent = new Intent(getActivity().getApplicationContext(),
				RestaurentDetails.class);
		intent.putExtra(DBHelperRestaurent.Id, MainActivity.restInfoList.get(position)
				.getId());
		intent.putExtra(DBHelperRestaurent.Name, MainActivity.restInfoList.get(position)
				.getName());
		intent.putExtra(DBHelperRestaurent.Address,
				MainActivity.restInfoList.get(position).getAddress());
		intent.putExtra(DBHelperRestaurent.Latitude,
				MainActivity.restInfoList.get(position).getLatitude());
		intent.putExtra(DBHelperRestaurent.Longitude,
				MainActivity.restInfoList.get(position).getLongitude());
		intent.putExtra(DBHelperRestaurent.Rank, MainActivity.restInfoList.get(position)
				.getRank());
		startActivity(intent);

	}
}
