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

public class CLVAdapterMenu extends ArrayAdapter<FoodItem> {

	private Context thisContext;

	public CLVAdapterMenu(Context context, int textViewResourceId,
			List<FoodItem> items) {
		super(context,textViewResourceId,items);
		thisContext=context;
	}
	
	private class ViewHolder{
		ImageView ivCLV;
		TextView tvName,tvPrice;
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
			holder.tvPrice=(TextView)convertView.findViewById(R.id.address_clv);
			
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();
		
		FoodItem rowItem = getItem(position);
		
		Integer id=R.drawable.ic_launcher;
		holder.ivCLV.setImageResource(id); 
		//Log.d("Inside getView(): CLVAdapter",id+"");
	
		holder.tvName.setText("Item Name: "+rowItem.getName());
		holder.tvPrice.setText("Price: "+rowItem.getPrice()+"");
		//holder.rateBar.setStepSize(rowItem.getRank());
		//holder.rateBar.setClickable(false);
		
		return convertView;
	}

}
