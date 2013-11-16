package com.example.foodbookbd;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.R.drawable;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends SherlockFragmentActivity {

	ActionBar actionbar;
	ActionBar.Tab tabRestaurents, tabFavourite, tabSearch, tabOffer, tabRate;
	FragmentRestaurent fragRestaurent;
	FragmentFavourite fragFavourite;
	FragmentSearch fragSearch;
	FragmentOffer fragOffer;
	FragmentRateIt fragRateIt;

	TextView tabText;
	ImageView tabImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);

		actionbar = getSupportActionBar();

		fragRestaurent = new FragmentRestaurent();
		fragFavourite = new FragmentFavourite();
		fragSearch = new FragmentSearch();
		fragOffer = new FragmentOffer();
		fragRateIt = new FragmentRateIt();


		tabRestaurents = actionbar.newTab();
		View viewTabRestaurent=getLayoutInflater().inflate(R.layout.tab_view, null, false);
		tabText=(TextView)viewTabRestaurent.findViewById(R.id.tab_text);
		tabImage=(ImageView)viewTabRestaurent.findViewById(R.id.tab_image);
		Drawable d = getResources().getDrawable(R.drawable.ic_launcher);
		tabImage.setImageDrawable(d);
		tabText.setText("Restaurent");
		tabRestaurents.setCustomView(viewTabRestaurent);
		//tabRestaurents.setText("Restaurents");
		//tabRestaurents.setIcon(null);
		
		tabFavourite = actionbar.newTab();
		/*View viewTabFavourite=getLayoutInflater().inflate(R.layout.tab_view, null, false);
		tabText=(TextView)viewTabFavourite.findViewById(R.id.tab_text);
		tabImage=(ImageView)viewTabFavourite.findViewById(R.id.tab_image);
		tabImage.setImageResource(R.drawable.favourite);
		tabText.setText("Favourite");
		tabFavourite.setCustomView(viewTabFavourite);*/
		tabFavourite.setText("Favourite");
		tabFavourite.setIcon(R.drawable.favourite);

		tabSearch = actionbar.newTab();
		/*View viewTabSearch=getLayoutInflater().inflate(R.layout.tab_view, null, false);
		tabText=(TextView)viewTabSearch.findViewById(R.id.tab_text);
		tabImage=(ImageView)viewTabSearch.findViewById(R.id.tab_image);
		tabImage.setImageDrawable(drawable);
		tabText.setText("Search");
		tabSearch.setCustomView(viewTabSearch);*/
		tabSearch.setText("Search");
		tabSearch.setIcon(R.drawable.ic_action_action_search);

		tabOffer = actionbar.newTab();
		/*View viewTabOffer=getLayoutInflater().inflate(R.layout.tab_view, null, false);
		tabText=(TextView)viewTabOffer.findViewById(R.id.tab_text);
		tabImage=(ImageView)viewTabOffer.findViewById(R.id.tab_image);
		tabImage.setImageResource(R.drawable.ic_launcher);
		tabText.setText("Offer");
		tabOffer.setCustomView(viewTabOffer);*/
		tabOffer.setText("Offer");
		tabOffer.setIcon(null);

		tabRate = actionbar.newTab();
		/*View viewTabRateIt=getLayoutInflater().inflate(R.layout.tab_view, null, false);
		tabText=(TextView)viewTabRateIt.findViewById(R.id.tab_text);
		tabImage=(ImageView)viewTabRateIt.findViewById(R.id.tab_image);
		tabImage.setImageResource(R.drawable.ic_action_rating_half_important);
		tabText.setText("Rate It!");
		tabRate.setCustomView(viewTabRateIt);*/
		tabRate.setText("Rate it!");
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
		
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		//actionbar.selectTab(tabRestaurents);

	}

	class MyTabListener implements TabListener {

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
			if (tab.getPosition() == 0) {
				ft.replace(android.R.id.content, fragRestaurent);
				//ft.commit();
			} else if (tab.getPosition() == 1) {
				ft.replace(android.R.id.content, fragFavourite);
				//ft.commit();
			} else if (tab.getPosition() == 2) {
				ft.replace(android.R.id.content, fragSearch);
				//ft.commit();
			} else if (tab.getPosition() == 3) {
				ft.replace(android.R.id.content, fragOffer);
				//ft.commit();
			} else if (tab.getPosition() == 4) {
				ft.replace(android.R.id.content, fragRateIt);
				//ft.commit();
			}
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
		
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub

		}

	}

}
