package com.food.delivery.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.food.delivery.facade.CustomerProfileFacade;
import com.food.delivery.facade.RestaurantProfileFacade;
import com.food.delivery.model.User;
import com.food.delivery.pojo.CustomerProfileResource;
import com.food.delivery.pojo.MenuResource;
import com.food.delivery.pojo.MessageResponse;
import com.food.delivery.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class CustomerProfileController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerProfileController.class);

	@Autowired
	private CustomerProfileFacade customerProfileFacade;

	@Autowired
	private RestaurantProfileFacade restaurantProfileFacade;

	@Autowired
	UserRepository userRepository;

	// POST
	@CrossOrigin(origins = "*", exposedHeaders = "**")
	@PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN')")
	@PostMapping(value = "/customerProfile", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> addCustomerProfile(@RequestBody CustomerProfileResource customerResource) {

		logger.info("Create customer profile");

		if (customerResource == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Empty customer profile resource!"));
		}

		// Call facade method to get books with optional search criteria
		customerProfileFacade.addCustomerProfile(customerResource);

		return ResponseEntity.ok(new MessageResponse("Book successfully added into the book store!"));
	}

	// GET - User profile info based on firstname of user (filter - true)
	@CrossOrigin(origins = "*", exposedHeaders = "**")
	@PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN')")
	@GetMapping(value = "/customerProfile/{firstname}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getCustomerProfiles(@PathVariable("firstname") String firstname,
			HttpServletRequest request) {

		logger.info("Get customer profile");

		if (firstname.isEmpty()) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Please provide firstname of the customer to view the profile."));
		}
		CustomerProfileResource customerProfile = new CustomerProfileResource();
		try {
			// Call facade method to save user profile
			customerProfile = customerProfileFacade.getCustomerProfile(firstname);

		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Error: User profile not found for user " + firstname + "!"));
		}

		return ResponseEntity.ok(customerProfile);
	}

	// PUT - Update user profile
	@CrossOrigin(origins = "*", exposedHeaders = "**")
	@PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN')")
	@PutMapping("/customerProfile")
	public ResponseEntity<?> updateCustomerProfile(@RequestBody CustomerProfileResource customerProfileResource) {

		if (customerProfileResource.getUsername() == null) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Error: provide username for updating a user's profile"));
		}

		Optional<User> user = userRepository.findByUsername(customerProfileResource.getUsername());

		if (user.isEmpty()) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username does not exist!"));

		}

		try {
			customerProfileFacade.updateCustomerProfile(customerProfileResource);
		} catch (Exception e) {
			return ResponseEntity.internalServerError()
					.body(new MessageResponse("Error: User profile could not be updated!"));
		}

		return ResponseEntity.ok(new MessageResponse("User Profile Updated successfully!"));
	}

	// GET - User profile info based on firstname of user (filter - true)
	@CrossOrigin(origins = "*", exposedHeaders = "**")
	@PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN')")
	@GetMapping(value = "/restaurants", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getRestaurantList(@RequestParam(value = "food_item", required = false) String food_item,
			@RequestParam(value = "veg", required = false) Boolean veg,
			@RequestParam(value = "cuisine", required = false) String cuisine, HttpServletRequest request) {

		List<MenuResource> restaurants = new ArrayList<MenuResource>();
		logger.debug("Get Restaurants' list");
		try {
			// Call facade method to save user profile
			restaurants = restaurantProfileFacade.getRestaurantList(food_item, veg, cuisine);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Restaurants could not be retrieved!"));
		}

		return ResponseEntity.ok(restaurants);
	}

	// DELETE - Delete customer profile based on customerId
//	@CrossOrigin(origins = "*", exposedHeaders = "**")
//	@PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN')")
//	@DeleteMapping(value = "/customerProfile", produces = { MediaType.APPLICATION_JSON_VALUE })
//	public ResponseEntity<?> deleteCustomerProfile(@RequestParam(value = "customerId") String username) {
//
//		logger.info("Delete customer profile");
//
//		if (username.isEmpty()) {
//			return ResponseEntity.badRequest().body(new MessageResponse("Error: Please provide customer username to delete customer profile."));
//		}
//
//		boolean isDeleted = customerProfileFacade.deleteCustomerProfile(username);
//
//		if (!isDeleted) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//					.body(new MessageResponse("Error: Customer profile could not be deleted or not found!"));
//		}
//
//		return ResponseEntity.ok(new MessageResponse("Customer profile deleted successfully!"));
//	}

}
