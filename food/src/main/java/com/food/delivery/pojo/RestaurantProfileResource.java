package com.food.delivery.pojo;

public class RestaurantProfileResource {

	private String firstname;

	private String lastname;

	private String username;

	private String restaurant_name;

	private String phone_no;

	private String address;

	private int hours_of_operation;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRestaurant_name() {
		return restaurant_name;
	}

	public void setRestaurant_name(String restaurant_name) {
		this.restaurant_name = restaurant_name;
	}

	public String getPhone_no() {
		return phone_no;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getHours_of_operation() {
		return hours_of_operation;
	}

	public void setHours_of_operation(int hours_of_operation) {
		this.hours_of_operation = hours_of_operation;
	}

}
