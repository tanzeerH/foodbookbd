package com.example.foodbookbd;

import com.example.foodbookbd.WelcomeActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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

public class ShowLocationInMapFragment extends Fragment implements
		OnMapClickListener {

	private GoogleMap googlemap;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.show_location_inmap, null, false);
		android.support.v4.app.FragmentManager fragmentManager = getActivity()
				.getSupportFragmentManager();
		SupportMapFragment mapFragment = (SupportMapFragment) fragmentManager
				.findFragmentById(R.id.locationmap);
		googlemap = mapFragment.getMap();
		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(WelcomeActivity.myLatitude,
						WelcomeActivity.myLongitude)).zoom(15).build();
		googlemap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));
		MarkerOptions marker = new MarkerOptions().position(
				new LatLng(WelcomeActivity.myLatitude,
						WelcomeActivity.myLongitude)).title("My position");
		googlemap.addMarker(marker);
		googlemap.setOnMapClickListener(this);

		return view;
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
				latlondialog.dismiss();
				FragmentAddNew.targetlat=templat;
				FragmentAddNew.targetlon=templng;
				Hide();

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

	@Override
	public void onMapClick(LatLng latlng) {
		showLatLonDialog(latlng);

	}
	public void Hide()
	{
		android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager()
				.beginTransaction();
		getActivity().getSupportFragmentManager().popBackStack();
		//transaction.commit();
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
	}

}
