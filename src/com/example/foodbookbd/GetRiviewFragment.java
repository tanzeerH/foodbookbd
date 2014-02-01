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

import com.example.foodbookbd.GetReview.SendReview;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class GetRiviewFragment extends Fragment implements OnClickListener {
	Button button;
	RatingBar ratingBar;
	EditText editTextName, editTextReview;
	long rest_idToGetReview;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.get_review,null, false);
		button = (Button)view.findViewById(R.id.btn_get_review);
		ratingBar = (RatingBar)view.findViewById(R.id.rb_get_review);
		ratingBar.setIsIndicator(false);
		ratingBar.setMax(5);
		ratingBar.setNumStars(5);
		ratingBar.setStepSize((float) 0.5);
		editTextName = (EditText)view.findViewById(R.id.et_get_reviewer_name);
		editTextReview = (EditText)view.findViewById(R.id.et_get_review);

		button.setOnClickListener(this);
		return view;
	}
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (v.getId() == R.id.btn_get_review) {

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
			
			/*ReviewItem reviewItem=new ReviewItem();
			reviewItem.setRestId(rest_idToGetReview);
			reviewItem.setRank(rate);
			reviewItem.setReviewerName(reviewerName);
			reviewItem.setReviewDetails(reviewDetails);
			//RestaurentDetailsReview.reviewList.add(reviewItem);*/
			
			
			String url="http://dimik.webege.com/foodbookdhaka/postreview.php";
			DefaultHttpClient client= new DefaultHttpClient();
			HttpPost httpPost=new HttpPost(url);
			
			ArrayList<NameValuePair> pairs= new ArrayList<NameValuePair>();
	
			JSONObject jsonObject=new JSONObject();
			try {
				jsonObject.put("Rest_id", rest_idToGetReview);
				jsonObject.put("Rate", rate);
				jsonObject.put("ReviewerName", reviewerName);
				jsonObject.put("ReviewDetails", reviewDetails);
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
			
			Toast.makeText(getActivity(), "Reveiw submitted.", Toast.LENGTH_LONG).show();
			//editTextReview.setText(msg);
		}

	}

}
