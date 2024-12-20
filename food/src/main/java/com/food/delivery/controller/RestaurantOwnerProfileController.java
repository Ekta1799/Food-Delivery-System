package com.food.delivery.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.food.delivery.facade.RestaurantProfileFacade;
import com.food.delivery.model.User;
import com.food.delivery.pojo.MenuResource;
import com.food.delivery.pojo.MessageResponse;
import com.food.delivery.pojo.RestaurantProfileResource;
import com.food.delivery.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class RestaurantOwnerProfileController {

	private static final Logger logger = LoggerFactory.getLogger(RestaurantOwnerProfileController.class);

	@Autowired
	private RestaurantProfileFacade restaurantProfileFacade;

	@Autowired
	UserRepository userRepository;

	// POST
	@CrossOrigin(origins = "*", exposedHeaders = "**")
	@PreAuthorize("hasAnyRole('ROLE_RESTAURANT_OWNER', 'ROLE_ADMIN')")
	@PostMapping(value = "/restaurantProfile", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> addRestaurantOwnerProfile(@RequestBody RestaurantProfileResource restaurantResource) {

		logger.debug("Creating Restaurant owner's profile");

		if (restaurantResource == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Empty restuarnt profile!"));
		}
		// Call facade method to get books with optional search criteria
		restaurantProfileFacade.addRestaurantProfile(restaurantResource);

		return ResponseEntity.ok(new MessageResponse("Restaurant profile created successfully!"));
	}

	// GET - User profile info based on firstname of user (filter - true)
	@CrossOrigin(origins = "*", exposedHeaders = "**")
	@PreAuthorize("hasAnyRole('ROLE_RESTAURANT_OWNER', 'ROLE_ADMIN')")
	@GetMapping(value = "/restaurantOwnerProfile/{firstname}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getRestaurantProfiles(@PathVariable(value = "firstname") String firstname,
			HttpServletRequest request) {

		if (firstname == null) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Please provide firstname of the resturant owner to view the profile."));
		}

		RestaurantProfileResource restaurantResource = new RestaurantProfileResource();
		try {
			// Call facade method to save user profile
			restaurantResource = restaurantProfileFacade.getRestaurantProfile(firstname);

		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Error: Restaurant owner profile not found for name " + firstname + "!"));
		}

		if (restaurantResource == null) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Restaurant owner profile not found for name " + firstname + "!"));
		}

		return ResponseEntity.ok(restaurantResource);
	}

	// PUT - Update restaurant owner's profile
	@CrossOrigin(origins = "*", exposedHeaders = "**")
	@PreAuthorize("hasAnyRole('ROLE_RESTAURANT_OWNER', 'ROLE_ADMIN')")
	@PutMapping("/restaurantProfile")
	public ResponseEntity<?> updateRestaurantProfile(@RequestBody RestaurantProfileResource restaurantResource) {

		logger.debug("Updating profile details for restuarant owner");

		if (restaurantResource.getUsername() == null) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Error: Provide username for updating a restaurant owner's profile"));
		}

		Optional<User> user = userRepository.findByUsername(restaurantResource.getUsername());

		if (user.isEmpty()) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username does not exist!"));
		}

		try {
			restaurantProfileFacade.updateRestaurantProfile(restaurantResource);
		} catch (Exception e) {
			return ResponseEntity.internalServerError()
					.body(new MessageResponse("Error: Restaurant owner profile could not be created!"));
		}

		return ResponseEntity.ok(new MessageResponse("Restaurant owner's profile updated successfully!"));
	}

	// POST
	@CrossOrigin(origins = "*", exposedHeaders = "**")
	@PreAuthorize("hasAnyRole('ROLE_RESTAURANT_OWNER')")
	@RequestMapping(value = "/menu", method = { RequestMethod.POST }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> addMenuByRestaurantName(@RequestBody List<MenuResource> menuResource) {

		logger.debug("Adding the list of menu");
		boolean error = false;
		try {
			// Call facade method to get books with optional search criteria
			error = restaurantProfileFacade.addMenu(menuResource);
		} catch (NullPointerException e) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Error: Please specify all the fields for adding the menu. "
							+ "Fields required : food_item, price, cuisine, veg, restaurant_name;"));
		}

		if (!error) {
			return ((BodyBuilder) ResponseEntity.notFound()).body(
					new MessageResponse("Error: The menu you are trying to add for the restaurant does not exist"));
		}

		return ResponseEntity.ok(new MessageResponse("Menu added successfully!"));
	}

}
