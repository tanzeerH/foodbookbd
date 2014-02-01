package com.example.foodbookbd;

public class RestaurentInfo {
	
	long id;
	String Name;
	String Address;
	double distance;
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	double longitude,latitude,rank;
	
	
	public double getRank() {
		return rank;
	}


	public void setRank(double rank) {
		this.rank = rank;
	}


	public RestaurentInfo(long id,String name, String address, double longitude,
			double latitude,double rank) {
		this.id=id;
		Name = name;
		Address = address;
		this.longitude = longitude;
		this.latitude = latitude;
		this.rank=rank;
	}
	
	public RestaurentInfo(){}


	@Override
	public String toString() {
		return "RestaurentInfo [id=" + id + ", Name=" + Name + ", Address="
				+ Address + ", longitude=" + longitude + ", latitude="
				+ latitude + ", rank=" + rank + "]";
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


	public double getLongitude() {
		return longitude;
	}


	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public double getLatitude() {
		return latitude;
	}


	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public void setDistance(double dis)
	{
		this.distance=dis;
	}
	public double getDistance() {
		
		return this.distance;
		
	}
	

}
