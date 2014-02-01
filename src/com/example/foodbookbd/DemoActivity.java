package com.example.foodbookbd;

import com.actionbarsherlock.app.SherlockActivity;
import java.util.ArrayList;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.example.foodbookbd.MainActivity.MyTabListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.ay;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import android.R.drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.view.View.OnClickListener;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.FragmentManager;

public class DemoActivity extends SherlockFragmentActivity implements
		AsyncResponse, GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {

	// tanzeer
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	private LocationClient locationclient;
	private Location mylocation;
	private LocationRequest locationrequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// tanzeer
		Log.v("msg", "hi");
		// Network Data

		CheckNetworkConnection checkcon = new CheckNetworkConnection(this);
		checkcon.delegate = this;
		checkcon.execute();

		// actionbar.selectTab(tabRestaurents);

	}

	public void processFinish(boolean ans) {
		Toast.makeText(this, "hello", Toast.LENGTH_LONG).show();
		Log.v("msg", "not connected");
		showDialog();

	}

	@Override
	public void hasconnection(boolean b) {
		Toast.makeText(this, "has", Toast.LENGTH_LONG).show();
		startLoacationTrack();

	}

	private void showDialog() {
		final Dialog onlinestatusdialog = new Dialog(this);
		onlinestatusdialog.setContentView(R.layout.dialog_onlinestatus);
		Button btnok = (Button) onlinestatusdialog.findViewById(R.id.buttonOK);
		TextView txtStatus = (TextView) onlinestatusdialog
				.findViewById(R.id.textstaus);
		txtStatus
				.setText("Unable to Connect to Internet.Press Ok to Terminate.");
		Log.v("msg", "inside show dialog");
		btnok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onlinestatusdialog.dismiss();
				finish();

			}
		});
		Log.v("msg", "before show");
		onlinestatusdialog.setTitle("Alert");
		onlinestatusdialog.show();
		Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		onlinestatusdialog.getWindow().setLayout(width, height);
	}

	public void startLoacationTrack() {
		// location Data
		// Toast.makeText(this,"startLocationTrack",Toast.LENGTH_LONG).show();
		locationclient = new LocationClient(this, this, this);
		locationrequest = LocationRequest.create();
		// Use high accuracy
		locationrequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		// Set the update interval to 10 seconds
		locationrequest.setInterval(10000);
		// Set the fastest update interval to 3 second
		locationrequest.setFastestInterval(3000);
		if (servicesConnected())
			locationclient.connect();

	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub
		getSupportMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		// UpdateDatabaseThread updateDatabase = new UpdateDatabaseThread();
		// updateDatabase.start();
		Log.d("start thread", "started");
		return true;
	}

	@Override
	public void onLocationChanged(Location location) {
		Toast.makeText(this,
				location.getLatitude() + "  " + location.getLongitude(),
				Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		if (connectionResult.hasResolution()) {
			try {
				// Start an Activity that tries to resolve the error
				connectionResult.startResolutionForResult(this,
						CONNECTION_FAILURE_RESOLUTION_REQUEST);
				/*
				 * Thrown if Google Play services canceled the original
				 * PendingIntent
				 */
			} catch (IntentSender.SendIntentException e) {
				// Log the error
				e.printStackTrace();
			}
		} else {
			/*
			 * If no resolution is available, display a dialog to the user with
			 * the error.
			 */

		}

	}

	@Override
	public void onConnected(Bundle arg0) {
		// mylocation = locationclient.getLastLocation();
		Toast.makeText(this, "connected", Toast.LENGTH_SHORT).show();
		locationclient.requestLocationUpdates(locationrequest, this);

	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case CONNECTION_FAILURE_RESOLUTION_REQUEST:

			break;

		default:
			break;
		}

	}

	public static class ErrorDialogFragment extends DialogFragment {
		private Dialog mdialog;

		public ErrorDialogFragment() {
			super();
			mdialog = null;
		}

		public void setDialog(Dialog dialog) {
			mdialog = dialog;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			return mdialog;
		}
	}

	private boolean servicesConnected() {
		// Check that Google Play services is available
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		// If Google Play services is available
		if (ConnectionResult.SUCCESS == resultCode) {
			// In debug mode, log the status
			Log.d("Location Updates", "Google Play services is available.");
			// Continue
			return true;
			// Google Play services was not available for some reason
		} else {
			// Get the error code
			// int errorCode = ConnectionResult.getErrorCode();
			// Get the error dialog from Google Play services
			Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
					resultCode, this, CONNECTION_FAILURE_RESOLUTION_REQUEST);

			// If Google Play services can provide an error dialog
			if (errorDialog != null) {
				// Create a new DialogFragment for the error dialog
				ErrorDialogFragment errorFragment = new ErrorDialogFragment();
				// Set the dialog in the DialogFragment
				errorFragment.setDialog(errorDialog);
				// Show the error dialog in the DialogFragment
				errorFragment.show(getSupportFragmentManager(),
						"Location Updates");

			}
			return false;
		}
	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	protected void onResume() {

		super.onResume();
	}

	@Override
	protected void onStop() {
		if (locationclient.isConnected())
			locationclient.disconnect();
		super.onStop();
	}

	@Override
	public void dataFetchComplete(boolean status) {
		// TODO Auto-generated method stub
		
	}

}
