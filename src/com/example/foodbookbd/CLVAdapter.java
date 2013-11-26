package com.example.foodbookbd;

import java.util.List;



import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class CLVAdapter extends ArrayAdapter<RestaurentInfo> {

	private Context thisContext;

	public CLVAdapter(Context context, int textViewResourceId,
			List<RestaurentInfo> items) {
		super(context,textViewResourceId,items);
		thisContext=context;
	}
	
	private class ViewHolder{
		ImageView ivCLV;
		TextView tvName,tvAddress,tvLatitude,tvLongitude;
		RatingBar rateBar;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) thisContext
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.clv_row, null);
			holder=new ViewHolder();
			holder.ivCLV=(ImageView)convertView.findViewById(R.id.ivclv);
			holder.tvName=(TextView)convertView.findViewById(R.id.name_clv);
			holder.tvAddress=(TextView)convertView.findViewById(R.id.address_clv);
			holder.tvLatitude=(TextView)convertView.findViewById(R.id.latitude_clv);
			holder.tvLongitude=(TextView)convertView.findViewById(R.id.longitude_clv);
			holder.rateBar=(RatingBar)convertView.findViewById(R.id.rating_clv);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();
		
		RestaurentInfo rowItem = getItem(position);
		
		Integer id=R.drawable.ic_launcher;
		holder.ivCLV.setImageResource(id); 
		//Log.d("Inside getView(): CLVAdapter",id+"");
	
		holder.tvName.setText(rowItem.getName());
		holder.tvAddress.setText(rowItem.getAddress());
		holder.tvLatitude.setText(Float.toString(rowItem.getLatitude()));
		holder.tvLongitude.setText(Float.toString(rowItem.getLongitude()));
		//holder.rateBar.setStepSize(rowItem.getRank());
		//holder.rateBar.setClickable(false);
		holder.rateBar.setNumStars(5);
		holder.rateBar.setRating(rowItem.getRank());
		holder.rateBar.setIsIndicator(true);
		
		Log.d("rate "+position, Float.toString( rowItem.getRank()));
		
		return convertView;
	}

}
