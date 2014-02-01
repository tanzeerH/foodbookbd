package com.example.foodbookbd;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FragmentAddNew extends Fragment implements OnClickListener {

	public static double targetlat;
	public static double targetlon;
	private EditText edtName;
	private EditText edtaddress;
	private Button btnShowInMap;
	private Button btnSave;
	private TextView txtlat;
	private TextView txtlng;
	private double reslat;
	private double reslng;

	private GoogleMap googlemap;
	Dialog mapdialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.v("msg", "fragrate");
		View view = inflater.inflate(R.layout.fragment_add_new, null, false);

		edtName = (EditText) view.findViewById(R.id.editTextName);
		edtaddress = (EditText) view.findViewById(R.id.editTextAdress);
		btnShowInMap = (Button) view.findViewById(R.id.buttonShowMap);
		btnSave = (Button) view.findViewById(R.id.buttonSave);
		txtlat = (TextView) view.findViewById(R.id.textlatitude);
		txtlng = (TextView) view.findViewById(R.id.textlongitude);
		
		txtlat.setText("Latitude: "+ targetlat);
		txtlng.setText("Longitude: "+targetlon);

		btnShowInMap.setOnClickListener(this);

		return view;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		targetlat=0;
		targetlon=0;
	}

	/*public void ShowLocationInMap() {
		mapdialog = new Dialog(getActivity());
		mapdialog.setContentView(R.layout.show_location_inmap);
		android.support.v4.app.FragmentManager fragmentManager = getActivity()
				.getSupportFragmentManager();
		SupportMapFragment mapFragment = (SupportMapFragment) fragmentManager
				.findFragmentById(R.id.locationmap);
		googlemap = mapFragment.getMap();
		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(mylat, mylon)).zoom(15).build();
		googlemap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));
		MarkerOptions marker = new MarkerOptions().position(
				new LatLng(mylat, mylon)).title("My position");
		googlemap.addMarker(marker);
		googlemap.setOnMapClickListener(this);

		mapdialog.setTitle("Show Location Map");
		mapdialog.show();
		Display display = ((WindowManager) getActivity().getSystemService(
				Context.WINDOW_SERVICE)).getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		mapdialog.getWindow().setLayout(width, height);

	}

	public void showLatLonDialog(LatLng latlon) {
		final Dialog latlondialog = new Dialog(getActivity());
		latlondialog.setContentView(R.layout.dialog_map_latlng);

		TextView txtlatlon = (TextView) latlondialog
				.findViewById(R.id.textLatlon);
		Button btnok = (Button) latlondialog.findViewById(R.id.buttonOk);
		Button btncancel = (Button) latlondialog
				.findViewById(R.id.buttonCancel);

		txtlatlon.setText("Latitude: " + latlon.latitude + " Longitude :"
				+ latlon.longitude);
		final double templat = latlon.latitude;
		final double templng = latlon.longitude;

		btnok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				reslat = templat;
				reslng = templng;
				setResLatlng();
				try {
					SupportMapFragment fragment = (SupportMapFragment) getActivity()
							.getSupportFragmentManager().findFragmentById(
									R.id.locationmap);
					if (fragment != null)
						getFragmentManager().beginTransaction()
								.remove(fragment).commit();

				} catch (IllegalStateException e) {
					// handle this situation because you are necessary will get
					// an exception here :-(
				}
				latlondialog.dismiss();
				mapdialog.dismiss();

			}
		});
		btncancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				latlondialog.dismiss();

			}
		});
		latlondialog.show();

		latlondialog.setTitle("Check Latitude longitude");
		Display display = ((WindowManager) getActivity().getSystemService(
				Context.WINDOW_SERVICE)).getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		latlondialog.getWindow().setLayout(width, height);
	}

	public void setLatLon(double lat, double lon) {
		this.mylat = lat;
		this.mylon = lon;
	}
	*/
	@Override
	public void onClick(View v) {

		//ShowLocationInMap();
		android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager()
				.beginTransaction();
		transaction.addToBackStack(null);
		transaction.hide(this);
		transaction.replace(android.R.id.content,new ShowLocationInMapFragment());
		transaction.commit();

	}

	/*@Override
	public void onMapClick(LatLng latlng) {
		showLatLonDialog(latlng);

	}

	public void setResLatlng() {
		txtlat.setText("Latitude: " + reslat);
		txtlng.setText("Longitude: " + reslng);
	}
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		try {
			SupportMapFragment fragment = (SupportMapFragment) getActivity()
					.getSupportFragmentManager().findFragmentById(R.id.locationmap);
			if (fragment != null)
				getFragmentManager().beginTransaction().remove(fragment)
						.commit();

		} catch (IllegalStateException e) {
			// handle this situation because you are necessary will get
			// an exception here :-(
		}
	}*/

}
