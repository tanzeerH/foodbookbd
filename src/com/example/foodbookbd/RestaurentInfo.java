package com.example.foodbookbd;

public class RestaurentInfo {
	
	String Name;
	String Address;
	float longitude,latitude,rank;
	
	
	public float getRank() {
		return rank;
	}


	public void setRank(float rank) {
		this.rank = rank;
	}


	public RestaurentInfo(String name, String address, float longitude,
			float latitude,float rank) {
		Name = name;
		Address = address;
		this.longitude = longitude;
		this.latitude = latitude;
		this.rank=rank;
	}




	@Override
	public String toString() {
		return "RestaurentInfo [Name=" + Name + ", Address=" + Address
				+ ", longitude=" + longitude + ", latitude=" + latitude
				+ ", rank=" + rank + "]";
	}


	public String getName() {
		return Name;
	}


	public void setName(String name) {
		Name = name;
	}


	public String getAddress() {
		return Address;
	}


	public void setAddress(String address) {
		Address = address;
	}


	public float getLongitude() {
		return longitude;
	}


	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}


	public float getLatitude() {
		return latitude;
	}


	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	
	

}
