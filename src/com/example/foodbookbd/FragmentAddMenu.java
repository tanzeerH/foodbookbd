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
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentAddMenu extends Fragment implements OnClickListener{
	public RestaurentInfo restaurent=null;
	TextView restname;
	EditText edtname;
	EditText edtprice;
	Button btnsave;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragmentaddmenu,null,false);
		
		restname=(TextView)view.findViewById(R.id.textView1);
		edtname=(EditText)view.findViewById(R.id.editText1);
		edtprice=(EditText)view.findViewById(R.id.editText2);
		btnsave=(Button)view.findViewById(R.id.button1);
		
		restname.setText("Resturant: " +restaurent.getName());
		btnsave.setOnClickListener(this);

		return view;
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.button1)
		{
			String name=edtname.getText().toString();
			int price=Integer.parseInt(edtprice.getText().toString());
			new AddMenuPost(name, price,restaurent.getId()).execute();
		}
	}
	public void setUiClean()
	{
		edtname.setText("");
		edtprice.setText("");
	}
	private class AddMenuPost extends AsyncTask<Void,Void,Void>
	{
		String name;
		int price;
		long id;
		String msg="";
		InputStream IS;
		public AddMenuPost(String n, int p,long id)
		{
			this.name=n;
			this.price=p;
			this.id=id;
			
		}
		@Override
		protected Void doInBackground(Void... params) {
			String url = "http://dimik.webege.com/foodbookdhaka/addmenu.php";

			HttpClient clinet = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			ArrayList<NameValuePair> key = new ArrayList<NameValuePair>();
			key.add(new BasicNameValuePair("name", "" +name));
			key.add(new BasicNameValuePair("price", ""+price));
			key.add(new BasicNameValuePair("id", ""+id));



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
			
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
			setUiClean();
			super.onPostExecute(result);
		}
		
	}

}
