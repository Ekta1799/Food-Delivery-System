package com.food.delivery.services;

import com.food.delivery.model.CustomerProfile;

public interface CustomerProfileService {
	
	public void addCustomerProfile(CustomerProfile customer);

	public CustomerProfile getCustomerProfile(String firstname);
}
