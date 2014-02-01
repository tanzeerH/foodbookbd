package com.example.foodbookbd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
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

import com.google.android.gms.internal.fn;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Choreographer.FrameCallback;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class RestaurentDetailsFragment extends Fragment implements
		OnClickListener {

	public RestaurentInfo restaurent = null;
	public ArrayList<FoodItem> foodItem = new ArrayList<FoodItem>();
	private Button btnmenu;
	private Button btnGetDirection;
	private Button btnAddMnu;
	private Button btnReviews;
	private Button btnfav;
	private TextView txtName;
	private TextView txtAdress;
	private TextView txtlat;
	private RatingBar ratebar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.menu, null, false);

		txtName = (TextView) view.findViewById(R.id.name_clv);
		txtName.setText(restaurent.getName());

		txtAdress = (TextView) view.findViewById(R.id.address_clv);
		txtAdress.setText("Address: " + restaurent.getAddress());

		txtlat = (TextView) view.findViewById(R.id.latitude_clv);
		txtlat.setText("Distance: "
				+ new DecimalFormat("##.##").format(restaurent.getDistance())
				+ " meter");

		ratebar = (RatingBar) view.findViewById(R.id.rating_clv);
		ratebar.setNumStars(5);
		ratebar.setRating((float) restaurent.getRank());

		btnmenu = (Button) view.findViewById(R.id.opmenu);
		btnGetDirection = (Button) view.findViewById(R.id.opgetdirection);
		btnAddMnu = (Button) view.findViewById(R.id.addmenu);
		btnReviews=(Button)view.findViewById(R.id.opreviews);
		btnfav=(Button)view.findViewById(R.id.opaddtofav);
		btnGetDirection.setOnClickListener(this);
		btnmenu.setOnClickListener(this);
		btnAddMnu.setOnClickListener(this);
		btnReviews.setOnClickListener(this);
		btnfav.setOnClickListener(this);

		return view;
	}
	
	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.opmenu) {
			RestaurentDetailsMenuFragment restaurentDetailsMenuFragment = new RestaurentDetailsMenuFragment();
			restaurentDetailsMenuFragment.restaurentInfo = this.restaurent;
			android.support.v4.app.FragmentTransaction transaction = getActivity()
					.getSupportFragmentManager().beginTransaction();
			transaction.addToBackStack(null);
			transaction.hide(this);
			transaction.replace(android.R.id.content,
					restaurentDetailsMenuFragment);
			transaction.commit();
		}

		if (v.getId() == R.id.opgetdirection) {
			FragmentGetDirection fragmentGetDirection = new FragmentGetDirection();
			fragmentGetDirection.resturant = this.restaurent;
			android.support.v4.app.FragmentTransaction transaction = getActivity()
					.getSupportFragmentManager().beginTransaction();
			transaction.addToBackStack(null);
			transaction.hide(this);
			transaction.replace(android.R.id.content, fragmentGetDirection);
			transaction.commit();

		}
		if(v.getId()==R.id.addmenu)
		{
			FragmentAddMenu fragmentAddMenu=new FragmentAddMenu();
			fragmentAddMenu.restaurent = this.restaurent;
			android.support.v4.app.FragmentTransaction transaction = getActivity()
					.getSupportFragmentManager().beginTransaction();
			transaction.addToBackStack(null);
			transaction.hide(this);
			transaction.replace(android.R.id.content,
					fragmentAddMenu);
			transaction.commit();
		}
		if(v.getId()==R.id.opreviews)
		{
			ReviewFragment reviewFragment=new ReviewFragment();
			reviewFragment.restaurent = this.restaurent;
			android.support.v4.app.FragmentTransaction transaction = getActivity()
					.getSupportFragmentManager().beginTransaction();
			transaction.addToBackStack(null);
			transaction.hide(this);
			transaction.replace(android.R.id.content,reviewFragment);
			transaction.commit();
		}
		if(v.getId()==R.id.opaddtofav)
		{
			DBHelperRestaurent db=new DBHelperRestaurent(getActivity());
			db.insert(restaurent);
			Toast.makeText(getActivity(),"This restaurents is add to your favourte list",Toast.LENGTH_LONG).show();
			
		}

	}
	

}
