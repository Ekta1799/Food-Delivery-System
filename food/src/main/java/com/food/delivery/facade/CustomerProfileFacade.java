package com.food.delivery.facade;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.food.delivery.model.CustomerProfile;
import com.food.delivery.model.User;
import com.food.delivery.pojo.CustomerProfileResource;
import com.food.delivery.repository.CustomerProfileRepository;
import com.food.delivery.repository.UserRepository;
import com.food.delivery.services.CustomerProfileService;

@Component
public class CustomerProfileFacade {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerProfileFacade.class);

	@Autowired
	private CustomerProfileService service;
	
	@Autowired
	private CustomerProfileRepository customerProfileRepo;
	
	@Autowired
	UserRepository userRepository;

	public void addCustomerProfile(CustomerProfileResource customerResource) {

		CustomerProfile customer = new CustomerProfile();
		customer.setFirstname(customerResource.getFirstname());
		customer.setLastname(customerResource.getLastname());
		customer.setUsername(customerResource.getUsername());
		customer.setAddress(customerResource.getAddress());
		customer.setPhone_no(customerResource.getPhone_no());

//		Long userId = userRepository.userByUsername(book.getUsername());
//		bookModel.setUser_id(userId);
//		logger.debug("user id = "+userId);

		service.addCustomerProfile(customer);

	}

	public CustomerProfileResource getCustomerProfile(String firstName) {

		CustomerProfile customerProfile = new CustomerProfile();

		try {
			customerProfile = service.getCustomerProfile(firstName);
		} catch (Exception e) {
			throw e;
		}

		CustomerProfileResource customerProfileResource = convertDTOtoResource(customerProfile);

		return customerProfileResource;

	}

	private CustomerProfileResource convertDTOtoResource(CustomerProfile customer) {
		CustomerProfileResource customerProfileResource = new CustomerProfileResource();

		customerProfileResource.setFirstname(customer.getFirstname());
		customerProfileResource.setLastname(customer.getLastname());
		customerProfileResource.setUsername(customer.getUsername());
		customerProfileResource.setAddress(customer.getAddress());
		customerProfileResource.setPhone_no(customer.getPhone_no());
		customerProfileResource.setPayment_type(customer.getPayment_type());
		
		return customerProfileResource;
	}

	public boolean updateCustomerProfile(CustomerProfileResource customerProfileResource) {

		String username = customerProfileResource.getUsername();
		String firstname = customerProfileResource.getFirstname();
		String lastname = customerProfileResource.getLastname();
		String phoneNo = customerProfileResource.getPhone_no();
		String address = customerProfileResource.getAddress();
		String payment = customerProfileResource.getPayment_type();
		
		logger.debug(payment);
		logger.debug("fn : " +firstname + " ln : " + lastname + " user: " + username);

		Optional<User> userData = userRepository.findByUsername(username);
		User userVal = userData.get();

		if (customerProfileRepo.existsByUserId(userVal.getId())) {

			Optional<CustomerProfile> customer = customerProfileRepo.findByUsername(username);
			CustomerProfile customer1 = customer.get();
			
			try {
				
				customerProfileRepo.updateUserProfile(customer1.getId(), firstname, lastname, phoneNo, address, payment);
			} catch (Exception e) {
				logger.error("Could not update user profile : {}", e);
				return false;
			}

		} else {
			logger.error("User profile does not exist for user ID: " + userVal.getId());
			return false;
		}
		return true;
	}
	
//	public boolean deleteCustomerProfile(String username) {
//		Optional<User> userData = userRepository.findByUsername(username);
//		User userVal = userData.get();
//		
//		if (customerProfileRepo.existsByUserId(userVal.getId())) {
//
//			Optional<CustomerProfile> customer = customerProfileRepo.findByUsername(username);
//			CustomerProfile customer1 = customer.get();
//			
//			try {
//				
//				customerProfileRepo.deleteCustomerProfile(customer1.getId());
//				
//				userRepository.deleteUser(customer1.getId());
//			} catch (Exception e) {
//				logger.error("Could not update user profile : {}", e);
//				return false;
//			}
//
//		} else {
//			logger.error("User profile does not exist for user ID: " + userVal.getId());
//			return false;
//		}
//		
//		return true;
//		
//	}

}
