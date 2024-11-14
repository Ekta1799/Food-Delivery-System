package com.food.delivery.pojo;

public class DeliveryPersonnelProfileResource {

	private String firstname;

	private String lastname;

	private String username;

	private String phone_no;

	private String vehicle_type;

	private boolean availability;

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

	public String getPhone_no() {
		return phone_no;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

	public String getVehicle_type() {
		return vehicle_type;
	}

	public void setVehicle_type(String vehicle_type) {
		this.vehicle_type = vehicle_type;
	}

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailablity(boolean availability) {
		this.availability = availability;
	}

}
