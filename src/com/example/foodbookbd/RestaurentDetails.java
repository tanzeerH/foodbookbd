package com.example.foodbookbd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;

public class RestaurentDetails extends SherlockActivity implements
		OnClickListener {

	Button addToFavourite,menuButton,btnOpeningTimes,btnReview,btnReport,btnGallery;
	TextView tvName,tvAddress;
	RatingBar ratebar;
	RestaurentInfo thisRestaurent;
	long id;
	String Name,Address;
	float Latitude,Longitude,Rank;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_restaurent);
		Intent intent=getIntent();
		id=(Long) intent.getSerializableExtra(DBHelperRestaurent.Id);
		Name=(String) intent.getSerializableExtra(DBHelperRestaurent.Name);
		Address=(String) intent.getSerializableExtra(DBHelperRestaurent.Address);
		Latitude=(Float) intent.getSerializableExtra(DBHelperRestaurent.Latitude);
		Longitude=(Float) intent.getSerializableExtra(DBHelperRestaurent.Longitude);
		Rank=(Float) intent.getSerializableExtra(DBHelperRestaurent.Rank);
		thisRestaurent=new RestaurentInfo(id, Name, Address, Longitude, Latitude, Rank);
		
		
		tvName=(TextView)findViewById(R.id.name_clv);
		tvName.setText(thisRestaurent.Name);
		tvAddress=(TextView)findViewById(R.id.address_clv);
		tvAddress.setText(thisRestaurent.Address);
		ratebar=(RatingBar)findViewById(R.id.rating_clv);
		ratebar.setRating(thisRestaurent.rank);
		
		
		addToFavourite=(Button) findViewById(R.id.add_to_favourite);
		addToFavourite.setOnClickListener(this);
		menuButton=(Button) findViewById(R.id.menu_button);
		menuButton.setOnClickListener(this);
		btnOpeningTimes=(Button)findViewById(R.id.opening_times);
		btnOpeningTimes.setOnClickListener(this);
		btnReview=(Button)findViewById(R.id.review_button);
		btnReview.setOnClickListener(this);
		btnReport=(Button)findViewById(R.id.report);
		btnReport.setOnClickListener(this);
		btnGallery=(Button)findViewById(R.id.gallery);
		btnGallery.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.menu_button)
		{
			Intent intent= new Intent(getApplicationContext(), RestaurentDetailsMenu.class);
			intent.putExtra(DBHelperRestaurent.Id, thisRestaurent.getId());
			startActivity(intent);
		}
		else if(v.getId()==R.id.add_to_favourite)
		{
			String[] columns={DBHelperRestaurent.Favourited};
			String[] values={Boolean.toString(true)};
			MainActivity.databaseAdapter.updateRow(thisRestaurent.id,columns,values);
		}
		else if(v.getId()==R.id.opening_times)
		{
		
		}
		else if(v.getId()==R.id.review_button)
		{
			Intent intent= new Intent(getApplicationContext(), RestaurentDetailsReview.class);
			intent.putExtra(DBHelperRestaurent.Id, thisRestaurent.getId());
			startActivity(intent);
		}
		else if(v.getId()==R.id.gallery)
		{}
		else if(v.getId()==R.id.report)
		{}
	
	}

}
