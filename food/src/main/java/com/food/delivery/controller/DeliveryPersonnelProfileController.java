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
import org.springframework.web.bind.annotation.RestController;

import com.food.delivery.facade.DeliveryPersonnelProfileFacade;
import com.food.delivery.model.User;
import com.food.delivery.pojo.DeliveryPersonnelProfileResource;
import com.food.delivery.pojo.DeliveryResource;
import com.food.delivery.pojo.MessageResponse;
import com.food.delivery.pojo.OrdersResource;
import com.food.delivery.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class DeliveryPersonnelProfileController {

	private static final Logger logger = LoggerFactory.getLogger(DeliveryPersonnelProfileController.class);

	@Autowired
	private DeliveryPersonnelProfileFacade deliveryPersonnelProfileFacade;

	@Autowired
	UserRepository userRepository;

	// POST
	@CrossOrigin(origins = "*", exposedHeaders = "**")
	@PreAuthorize("hasAnyRole('ROLE_DELIVERY_PERSONNEL', 'ROLE_ADMIN')")
	@PostMapping(value = "/deliveryPersonnelProfile", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> addDeliveryPersonnelProfile(
			@RequestBody DeliveryPersonnelProfileResource deliveryPersonnelProfileResource) {

		logger.info("Create Delivery Personnel profile");

		if (deliveryPersonnelProfileResource == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Empty delivery personnel profile resource!"));
		}

		deliveryPersonnelProfileFacade.addDeliveryPersonnelProfile(deliveryPersonnelProfileResource);

		return ResponseEntity.ok(new MessageResponse("Delivery Personnel profile added successfully"));
	}

	// GET - Delivery pErsonnel profile info based on firstname of user (filter -
	// true)
	@CrossOrigin(origins = "*", exposedHeaders = "**")
	@PreAuthorize("hasAnyRole('ROLE_DELIVERY_PERSONNEL', 'ROLE_ADMIN')")
	@GetMapping(value = "/deliveryPersonnelProfile/{firstname}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getDeliveryPersonnelProfile(@PathVariable("firstname") String firstname,
			HttpServletRequest request) {

		logger.info("Get delivery personnel profile");

		if (firstname.isEmpty()) {
			return ResponseEntity.badRequest().body(
					new MessageResponse("Please provide firstname of the delivery personnel to view the profile."));
		}
		DeliveryPersonnelProfileResource deliveryPersonnelProfileResource = new DeliveryPersonnelProfileResource();
		try {
			// Call facade method to save user profile
			deliveryPersonnelProfileResource = deliveryPersonnelProfileFacade.getDeliveryPersonnelProfile(firstname);

		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Error: User profile not found for user " + firstname + "!"));
		}

		return ResponseEntity.ok(deliveryPersonnelProfileResource);
	}

	// PUT - Update user profile
	@CrossOrigin(origins = "*", exposedHeaders = "**")
	@PreAuthorize("hasAnyRole('ROLE_DELIVERY_PERSONNEL', 'ROLE_ADMIN')")
	@PutMapping("/deliveryPersonnelProfile")
	public ResponseEntity<?> updateDeliveryPersonnelProfile(
			@RequestBody DeliveryPersonnelProfileResource deliveryPersonnelProfileResource) {

		if (deliveryPersonnelProfileResource.getUsername() == null) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Error: provide username for updating a delivery personnel's profile"));
		}

		Optional<User> user = userRepository.findByUsername(deliveryPersonnelProfileResource.getUsername());

		if (user.isEmpty()) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username does not exist!"));

		}

		try {
			deliveryPersonnelProfileFacade.updateDeliveryPersonnelProfile(deliveryPersonnelProfileResource);
		} catch (Exception e) {
			return ResponseEntity.internalServerError()
					.body(new MessageResponse("Error: Delivery personnel's profile could not be updated!"));
		}

		return ResponseEntity.ok(new MessageResponse("Delivery personnel's Profile Updated successfully!"));
	}

	// GET - User profile info based on firstname of user (filter - true)
	@CrossOrigin(origins = "*", exposedHeaders = "**")
	@PreAuthorize("hasAnyRole('ROLE_DELIVERY_PERSONNEL', 'ROLE_ADMIN')")
	@GetMapping(value = "/deliveryOrders", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getOrdersForDelivery(HttpServletRequest request) {

		List<OrdersResource> orders = new ArrayList<OrdersResource>();
		logger.debug("Get Restaurants' list");
		try {
			// Call facade method
			orders = deliveryPersonnelProfileFacade.getDeliveryOrders();

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Orders list could not be retrieved!"));
		}

		return ResponseEntity.ok(orders);
	}

	// POST
	@CrossOrigin(origins = "*", exposedHeaders = "**")
	@PreAuthorize("hasAnyRole('ROLE_DELIVERY_PERSONNEL', 'ROLE_ADMIN')")
	@PostMapping(value = "/deliveryDetails", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> addDeliveryDetails(@RequestBody DeliveryResource deliveryResource) {

		logger.info("Adding delivery details");

		if (deliveryResource.getUsername() == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Please provide username!"));
		}

		deliveryPersonnelProfileFacade.addDeliveryDetails(deliveryResource);

		return ResponseEntity.ok(new MessageResponse("Delivery Details added successfully"));
	}
	
	// PUT - Update Delivery Status
	@CrossOrigin(origins = "*", exposedHeaders = "**")
	@PreAuthorize("hasAnyRole('ROLE_DELIVERY_PERSONNEL', 'ROLE_ADMIN')")
	@PutMapping("/deliveryStatus/{deliveryPersonnelUsername}")
	public ResponseEntity<?> updateDeliveryStatus(@PathVariable("deliveryPersonnelUsername") String username, 
			@RequestBody String status) {

		if (status == null) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Error: Please provide status tp update delivery status"));
		}

		Optional<User> user = userRepository.findByUsername(username);

		if (user.isEmpty()) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username does not exist!"));

		}

		try {
			deliveryPersonnelProfileFacade.updateDeliveryStatus(status, user.get().getId());
		} catch (Exception e) {
			return ResponseEntity.internalServerError()
					.body(new MessageResponse("Error: Delivery Status could not be updated!"));
		}

		return ResponseEntity.ok(new MessageResponse("Delivery Status Updated successfully!"));
	}

}
