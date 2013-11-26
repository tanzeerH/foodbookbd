package com.example.foodbookbd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
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
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
}
