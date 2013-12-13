package com.example.foodbookbd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.hardware.Camera.Area;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

public class GetReview extends SherlockActivity implements OnClickListener {

	Button button;
	RatingBar ratingBar;
	EditText editTextName, editTextReview;
	long rest_idToGetReview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Intent intent=getIntent();
		rest_idToGetReview=(Long)intent.getSerializableExtra(DBHelperReview.RestId);
		
		
		setContentView(R.layout.get_review);

		button = (Button) findViewById(R.id.btn_get_review);
		ratingBar = (RatingBar) findViewById(R.id.rb_get_review);
		ratingBar.setIsIndicator(false);
		ratingBar.setMax(5);
		ratingBar.setNumStars(5);
		ratingBar.setStepSize((float) 0.5);
		editTextName = (EditText) findViewById(R.id.et_get_reviewer_name);
		editTextReview = (EditText) findViewById(R.id.et_get_review);

		button.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (v.getId() == R.id.btn_get_review) {
			/*Toast.makeText(
					getApplicationContext(),
					ratingBar.getRating() + " "
							+ editTextName.getText().toString() + " "
							+ editTextReview.getText().toString(),
					Toast.LENGTH_LONG).show();*/

			SendReview sendReview = new SendReview();
			sendReview.execute(ratingBar.getRating() + "", editTextName
					.getText().toString(), editTextReview.getText().toString());
		}

	}

	class SendReview extends AsyncTask<String, Void, JSONObject> {

		
		String msg="";
		@Override
		protected JSONObject doInBackground(String... params) {
			
			float rate=Float.valueOf(params[0]);
			String reviewerName=params[1];
			String reviewDetails=params[2];
			
			ReviewItem reviewItem=new ReviewItem();
			reviewItem.setRestId(rest_idToGetReview);
			reviewItem.setRank(rate);
			reviewItem.setReviewerName(reviewerName);
			reviewItem.setReviewDetails(reviewDetails);
			RestaurentDetailsReview.reviewList.add(reviewItem);
			
			
			String url="http://dimik.webege.com/foodbookdhaka/postreview.php";
			DefaultHttpClient client= new DefaultHttpClient();
			HttpPost httpPost=new HttpPost(url);
			
			ArrayList<NameValuePair> pairs= new ArrayList<NameValuePair>();
	
			JSONObject jsonObject=new JSONObject();
			try {
				jsonObject.put(DBHelperReview.RestId, rest_idToGetReview);
				jsonObject.put(DBHelperReview.Rate, rate);
				jsonObject.put(DBHelperReview.ReviewerName, reviewerName);
				jsonObject.put(DBHelperReview.ReviewDetails, reviewDetails);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			pairs.add(new BasicNameValuePair("json", jsonObject.toString()));
			
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(pairs));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				HttpResponse response=client.execute(httpPost);
				HttpEntity entity=response.getEntity();
				InputStream is= entity.getContent();
				
				
				
				BufferedReader br=new BufferedReader(new InputStreamReader(is));
				String line="";
				while((line=br.readLine())!=null)
				{
					msg+=line;
				}
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			return jsonObject;
		}
		@Override
		protected void onPostExecute(JSONObject jsonObject) {
			// TODO Auto-generated method stub
			
			Toast.makeText(getApplicationContext(), "Reveiw submitted.", Toast.LENGTH_LONG).show();
			//editTextReview.setText(msg);
		}

	}

}
