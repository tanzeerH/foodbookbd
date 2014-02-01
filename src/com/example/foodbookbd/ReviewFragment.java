package com.example.foodbookbd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

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

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ReviewFragment extends Fragment implements OnClickListener {
	ListView lvReview;
	CLVAdapterReview adapter;
	RestaurentInfo restaurent=null;
	public static ArrayList<ReviewItem> reviewList;
	Button button;
	long rest_idToGetReview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.details_restaurent_review, null,
				false);
		lvReview = (ListView) view.findViewById(R.id.review_list);
			reviewList=new ArrayList<ReviewItem>();

		button = (Button) view.findViewById(R.id.btn_add_new_review);
		button.setOnClickListener(this);
		new FetchReviws(restaurent.getId()).execute();
		return view;
	}

	@Override
	public void onClick(View v) {

		GetRiviewFragment getRiviewFragment=new GetRiviewFragment();
		getRiviewFragment.rest_idToGetReview = this.restaurent.getId();
		android.support.v4.app.FragmentTransaction transaction = getActivity()
				.getSupportFragmentManager().beginTransaction();
		transaction.addToBackStack(null);
		transaction.hide(this);
		transaction.replace(android.R.id.content,getRiviewFragment);
		transaction.commit();
	}
	public void setAdapter()
	{
		adapter = new CLVAdapterReview(getActivity(),
		R.layout.restaurent_review,reviewList);
		lvReview.setAdapter(adapter);
	}
	private class FetchReviws extends AsyncTask<Void, Void, Void> {

		InputStream IS;
		String msg = "";
		JSONArray jsonArray;
		long id;

		 public FetchReviws(long i) {
			this.id = i;
		}

		@Override
		protected Void doInBackground(Void... params) {
			String url = "http://dimik.webege.com/foodbookdhaka/restaurent_review_get.php";
			HttpClient clinet = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			ArrayList<NameValuePair> key = new ArrayList<NameValuePair>();
			key.add(new BasicNameValuePair("json", "" +id));

			try {
				httppost.setEntity(new UrlEncodedFormEntity(key));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				HttpResponse response = clinet.execute(httppost);
				HttpEntity entity = response.getEntity();
				IS = entity.getContent();

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			BufferedReader bf = new BufferedReader(new InputStreamReader(IS));
			String line = "";

			try {
				while ((line = bf.readLine()) != null) {
					msg = msg + line;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			msg = msg.substring(0, msg.length() - 146);
			try {
				jsonArray = new JSONArray(msg);

				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObject = jsonArray.getJSONObject(i);
					long id = jsonObject.getLong("Id");
					Log.d("id of" + id, id + "");
					long rest_id = jsonObject.getLong("Rest_id");
					String reviewerName = jsonObject
							.getString("ReviewerName");
					String reviewDetails = jsonObject
							.getString("ReviewDetails");
					long rank = jsonObject.getLong("Rate");
					ReviewItem reviewItem = new ReviewItem(id, rest_id,
							rank, reviewerName, reviewDetails);
					reviewList.add(reviewItem);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Log.v("msg", msg);
			Log.v("y",""+reviewList.size());
			Toast.makeText(getActivity(),
					"Complete" + reviewList.size() + msg,
					Toast.LENGTH_LONG).show();
			//Collections.reverse(reviewList);
			setAdapter();
			
			super.onPostExecute(result);
		}

	}

}
