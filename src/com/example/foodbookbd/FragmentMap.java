package com.example.foodbookbd;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentMap extends Fragment {
	private GoogleMap googlemap;
	private ArrayList<ResturantData> nearestList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_map, null, false);
		android.support.v4.app.FragmentManager fragmentManager = getActivity()
				.getSupportFragmentManager();
		SupportMapFragment mapFragment = (SupportMapFragment) fragmentManager
				.findFragmentById(R.id.map);
		//Fragment mapFragment=view.findViewById(R.id.map);
		googlemap = mapFragment.getMap();
		
		//get the list;
	

		
		Log.v("msg", "actvity started");

		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(WelcomeActivity.myLatitude,WelcomeActivity.myLongitude)).zoom(12).build();
		googlemap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));
		MarkerOptions marker = new MarkerOptions().position(
				new LatLng(WelcomeActivity.myLatitude,WelcomeActivity.myLongitude)).title("ME");
		marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
		
		//circle code
		CircleOptions circleOptions=new CircleOptions();
		circleOptions.center(new LatLng(WelcomeActivity.myLatitude,WelcomeActivity.myLongitude));
		circleOptions.radius(7500);
		circleOptions.strokeColor(Color.BLUE);
		Circle circle=googlemap.addCircle(circleOptions);
		googlemap.addMarker(marker);
		setMarker(WelcomeActivity.nearestlist);

		
		/*for(int i=0;i<nearestList.size();i++)
		{
			new ShowPath(nearestList.get(i)).execute();
		}*/

		return view;
	}
	public void setMarker(ArrayList<RestaurentInfo> list)
	{
		int i;
		for(i=0;i<list.size();i++)
		{
			RestaurentInfo temp=list.get(i);
			MarkerOptions marker = new MarkerOptions().position(
					new LatLng(temp.getLatitude(),temp.getLongitude())).title(temp.getName()+"\n"+temp.getAddress());
			googlemap.addMarker(marker);
			
		}
	}
	public void setList(ArrayList<ResturantData> list)
	{
		this.nearestList=list;
		
	}
	
	private class ShowPath extends AsyncTask<Void, Void, Void> {
		PolylineOptions rectLine;
		org.w3c.dom.Document doc = null;
		ResturantData mycls;
			

		public ShowPath(ResturantData temp) {
			this.mycls=temp;

		}

		@Override
		protected Void doInBackground(Void... params) {

		
			GMapV2Direction md = new GMapV2Direction();

			LatLng fromPosition = new LatLng(WelcomeActivity.myLatitude,WelcomeActivity.myLongitude);
			LatLng toPosition = new LatLng(mycls.getLatitude(),mycls.getLongitude());

			doc = md.getDocument(fromPosition, toPosition,
					GMapV2Direction.MODE_DRIVING);

			ArrayList<LatLng> directionPoint = md.getDirection(doc);
			rectLine = new PolylineOptions().width(3).color(Color.RED);

			for (int i = 0; i < directionPoint.size(); i++) {
				rectLine.add(directionPoint.get(i));
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			googlemap.addPolyline(rectLine);
			System.out.println("hello");
			System.out.println(doc.toString());

		}
	}
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		try {
	        SupportMapFragment fragment = (SupportMapFragment) getActivity()
	                                          .getSupportFragmentManager().findFragmentById(
	                                              R.id.map);
	        if (fragment != null) getFragmentManager().beginTransaction().remove(fragment).commit();

	    } catch (IllegalStateException e) {
	        //handle this situation because you are necessary will get 
	        //an exception here :-(
	    }
	}
}
