package com.example.foodbookbd;

public class FoodItem {

	public FoodItem() {

	}
	String Name;
	int price;
	long rest_Id;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public long getRest_Id() {
		return rest_Id;
	}
	public void setRest_Id(long rest_Id) {
		this.rest_Id = rest_Id;
	}
	@Override
	public String toString() {
		return "FoodItem [Name=" + Name + ", price=" + price + ", rest_Id="
				+ rest_Id + "]";
	}
	public FoodItem(long rest_id,String name, int price){
		Name = name;
		this.price = price;
		this.rest_Id = rest_id;
	}
	
	
	
}
