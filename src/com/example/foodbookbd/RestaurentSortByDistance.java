package com.example.foodbookbd;

import java.util.Comparator;

public class RestaurentSortByDistance implements Comparator<RestaurentInfo> {

	@Override
	public int compare(RestaurentInfo res1, RestaurentInfo res2) {
		
		if(res1.getDistance()==res2.getDistance())
			return 0;
		else
			if(res1.getDistance()>res2.getDistance())
				return 1;
			else 
				if(res1.getDistance()<res2.getDistance())
					return -1;
		
		return 0;
		
	}
	
	

}
