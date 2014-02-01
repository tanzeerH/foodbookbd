package com.example.foodbookbd;

import java.util.List;

import com.google.android.gms.internal.cn;

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

public class CLVAdapterReview extends ArrayAdapter<ReviewItem> {

	private Context thisContext;

	public CLVAdapterReview(Context context, int textViewResourceId,
			List<ReviewItem> items) {
		super(context, textViewResourceId, items);
		thisContext = context;
	}

	private class ViewHolder {
		RatingBar rate;
		TextView tvReviewerName, tvDetailsReview;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) thisContext
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.restaurent_review, null);
			holder=new ViewHolder();
			holder.rate=(RatingBar)convertView.findViewById(R.id.given_rating);
			holder.tvDetailsReview=(TextView)convertView.findViewById(R.id.details_review);
			holder.tvReviewerName=(TextView)convertView.findViewById(R.id.name_of_reviewer);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();
		
		ReviewItem rowItem = getItem(position);
		
		Integer id=R.drawable.ic_launcher;
		//Log.d("Inside getView(): CLVAdapter",id+"");
	
		holder.tvReviewerName.setText(rowItem.getReviewerName());
		holder.tvDetailsReview.setText(rowItem.getReviewDetails());
		holder.rate.setNumStars(5);
		holder.rate.setRating(rowItem.getRank());
	
		//holder.rate.setStepSize(rowItem.getRank());
		holder.rate.setClickable(false);
		
		return convertView;
	}
}
