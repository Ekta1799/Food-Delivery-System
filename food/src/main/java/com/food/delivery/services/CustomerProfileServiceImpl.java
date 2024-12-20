package com.food.delivery.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.delivery.model.CustomerProfile;
import com.food.delivery.repository.CustomerProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerProfileServiceImpl implements CustomerProfileService {

	@Autowired
	CustomerProfileRepository customerProfileRepo;

	public void addCustomerProfile(CustomerProfile customer) {
		customerProfileRepo.save(customer);
	}

	public CustomerProfile getCustomerProfile(String firstname) {
		CustomerProfile customerProfile = new CustomerProfile();

		customerProfile = customerProfileRepo.getCustomerProfile(firstname);

		return customerProfile;
	}

}
