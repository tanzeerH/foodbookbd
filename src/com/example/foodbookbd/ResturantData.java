package com.example.foodbookbd;

public class ResturantData {
	
	private String name;
	private String address;
	private double latitude;
	private double longitude;

	public ResturantData(String  Name, String Address, double lat, double lon) {
		name=Name;
		address=Address;
		latitude=lat;
		longitude=lon;
			
	}
	public String getName() {
			
		return this.name;
	}
	public String getAddress() {
		
		return this.address;
	}
	public double getLatitude() {
		
		return this.latitude;
	}
	public double getLongitude() {
		
		return this.longitude;
	}

}
