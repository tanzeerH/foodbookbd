package com.example.foodbookbd;

import java.util.ArrayList;

import android.location.Location;
import android.widget.Toast;

public class CalculateNearest {
	
	public CalculateNearest() {
		
	}
	public ArrayList<RestaurentInfo> getNearest(ArrayList<RestaurentInfo> resturantList) {
		ArrayList<RestaurentInfo> nearestList=new ArrayList<RestaurentInfo>();
		
		double maxDistance=7000;
		int i;
		for(i=0;i<resturantList.size();i++)
		{
			double dis=getDistance(WelcomeActivity.myLatitude,WelcomeActivity.myLongitude,resturantList.get(i).getLatitude(),resturantList.get(i).getLongitude());
			WelcomeActivity.restInfoList.get(i).setDistance(dis);
			if(dis<=maxDistance)
				nearestList.add(resturantList.get(i));
		}
			
		
		return nearestList;
	}
	public double getDistance(double fromlat,double fromlon,double tolat,double tolon)
	{
		Location locA=new Location("locA");
		locA.setLatitude(fromlat);
		locA.setLongitude(fromlon);
		
		Location locB=new Location("LocB");
		
		locB.setLatitude(tolat);
		locB.setLongitude(tolon);
		
		double distance=locA.distanceTo(locB);
		
		return distance;
		
		
	}

}
