package com.example.foodbookbd;

import java.util.ArrayList;
import java.util.zip.DataFormatException;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
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
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

public class MainActivity extends SherlockFragmentActivity {

	android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
	ActionBar actionbar;
	ActionBar.Tab tabRestaurents, tabFavourite, tabSearch, tabOffer, tabRate;
	FragmentRestaurent fragRestaurent;
	FragmentFavourite fragFavourite;
	FragmentSearch fragSearch;
	FragmentMap fragmap;
	FragmentAddNew fragAddNew;
	LocationManager locationManager;

	public static ArrayList<RestaurentInfo> restInfoList;
	public static DataBaseAdapterRestaurent databaseAdapter;
	TextView tabText;
	ImageView tabImage;
	// tanzeer
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);

		restInfoList = new ArrayList<RestaurentInfo>();
		databaseAdapter = new DataBaseAdapterRestaurent(getApplicationContext());

		actionbar = getSupportActionBar();
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		fragRestaurent = new FragmentRestaurent();
		fragFavourite = new FragmentFavourite();
		fragSearch = new FragmentSearch();
		fragmap = new FragmentMap();
		fragAddNew = new FragmentAddNew();

		tabRestaurents = actionbar.newTab();

		tabRestaurents.setText("Restaurents");
		tabRestaurents.setIcon(null);

		tabFavourite = actionbar.newTab();

		tabFavourite.setText("Favourite");
		tabFavourite.setIcon(R.drawable.favourite);

		tabSearch = actionbar.newTab();

		tabSearch.setText("Search");
		tabSearch.setIcon(R.drawable.ic_action_action_search);

		tabOffer = actionbar.newTab();
		tabOffer.setText("Map");
		tabOffer.setIcon(null);

		tabRate = actionbar.newTab();

		tabRate.setText("Add New");
		tabRate.setIcon(R.drawable.ic_action_rating_half_important);

		tabRestaurents.setTabListener(new MyTabListener());
		tabFavourite.setTabListener(new MyTabListener());
		tabSearch.setTabListener(new MyTabListener());
		tabOffer.setTabListener(new MyTabListener());
		tabRate.setTabListener(new MyTabListener());

		actionbar.addTab(tabRestaurents);
		actionbar.addTab(tabFavourite);
		actionbar.addTab(tabSearch);
		actionbar.addTab(tabOffer);
		actionbar.addTab(tabRate);
		// tanzeer

	}

	class MyTabListener implements TabListener {

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
				FragmentManager fmanager=getSupportFragmentManager();
				int num=fmanager.getBackStackEntryCount();
				while(num>0)
				{
					fmanager.popBackStack();
					num=num-1;
					
				}
			if (tab.getPosition() == 0) {
				ft.replace(android.R.id.content, fragRestaurent);
				// ft.commit();
			} else if (tab.getPosition() == 1) {
				ft.replace(android.R.id.content, fragFavourite);
				// ft.commit();
			} else if (tab.getPosition() == 2) {
				ft.replace(android.R.id.content, fragSearch);
				// ft.commit();
			} else if (tab.getPosition() == 3) {
				ft.replace(android.R.id.content, fragmap);
				// ft.commit();
			} else if (tab.getPosition() == 4) {
				ft.replace(android.R.id.content, fragAddNew);
				// ft.commit();
			}
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {

		}

	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {

		getSupportMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		UpdateDatabaseThread updateDatabase = new UpdateDatabaseThread();
		updateDatabase.start();
		Log.d("start thread", "started");
		return true;
	}

	// after some logic

	public ArrayList<ResturantData> tempLoadData() {
		ArrayList<ResturantData> list = new ArrayList<ResturantData>();
		list.add(new ResturantData("Ar Rahmania", "South Shahjahanpur",
				23.74430281550731, 90.4259676039328));
		list.add(new ResturantData("Boomers Cafe", "Baily road",
				23.741759527108194, 90.41010470697074));
		list.add(new ResturantData("Ice Cream and Fudge factory", "Baily road",
				23.74185933112472, 90.40977305208798));

		return list;

	}


	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

}
