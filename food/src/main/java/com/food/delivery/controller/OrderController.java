package com.food.delivery.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.food.delivery.facade.OrdersFacade;
import com.food.delivery.model.Menu;
import com.food.delivery.model.RestaurantOwnerProfile;
import com.food.delivery.model.Status;
import com.food.delivery.pojo.MessageResponse;
import com.food.delivery.pojo.OrdersResource;
import com.food.delivery.repository.MenuRepository;
import com.food.delivery.repository.OrdersRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class OrderController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerProfileController.class);

	@Autowired
	private OrdersFacade ordersFacade;

	@Autowired
	MenuRepository menuRepo;

	@Autowired
	OrdersRepository ordersRepo;

	// POST
	@CrossOrigin(origins = "*", exposedHeaders = "**")
	@PreAuthorize("hasAnyRole('ROLE_CUSTOMER')")
	@PostMapping(value = "/orders", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> addOrder(@RequestBody OrdersResource ordersResource) {

		logger.info("Add order details");

		if (ordersResource == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Empty orders resource!"));
		}

		if (ordersResource.getUsername() == null) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Bad Request: Please provide customer's username!"));
		}

		if (ordersResource.getRestaurantName() == null) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Bad Request: Please provide restaurant name!"));
		}

		if (ordersResource.getFood_price() == 0) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Bad Request: Please provide valid food price!"));
		}

		Menu menu = new Menu();
		try {
			menu = menuRepo.getMenuFromFoodName(ordersResource.getFoodName());
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Bad Request: Please provide valid food item!"));
		}

		if (menu.getPrice() != ordersResource.getFood_price()) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Bad Request: The food price entered is wrong!"));
		}

		try {
			ordersFacade.addOrders(ordersResource);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(new MessageResponse("Error: Order could be added!"));
		}

		return ResponseEntity.ok(new MessageResponse("Orders added successfully!"));
	}

	// Get Orders list for a particular restaurant
	@CrossOrigin(origins = "*", exposedHeaders = "**")
	@PreAuthorize("hasRole('ROLE_RESTAURANT_OWNER')")
	@GetMapping(value = "/restaurants/orders", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getOrders(@RequestParam(value = "restaurant_name", required = true) String restaurant_name,
			HttpServletRequest request) {

		logger.info("Get orders from restuarant name");

		if (restaurant_name.isEmpty()) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Please provide restaurant_name to view the orders list."));
		}
		List<OrdersResource> ordersList = new ArrayList<OrdersResource>();
		try {
			// Call facade method to save user profile
			ordersList = ordersFacade.getOrdersByRestaurantOwner(restaurant_name);

		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Error: order list could not be retrieved " + restaurant_name + "!"));
		}

		if (ordersList.isEmpty()) {
			return ((BodyBuilder) ResponseEntity.notFound())
					.body(new MessageResponse("No orders found for " + restaurant_name + "!"));
		}

		return ResponseEntity.ok(ordersList);
	}

	// Get Orders list for a customer (tracking order)
	@CrossOrigin(origins = "*", exposedHeaders = "**")
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	@GetMapping(value = "/customers/orders", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getOrderStatusByCustomer(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "status", required = false) String status, HttpServletRequest request) {

		logger.info("Tracking order status for the customer");

		if (username.isEmpty()) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Please provide username to view the order status."));
		}
		List<OrdersResource> ordersList = new ArrayList<OrdersResource>();
		try {
			// Call facade method to save user profile
			ordersList = ordersFacade.getOrderStatusByCustomer(username, status);

		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Error: order status could not be retrieved " + username + "!"));
		}

		if (ordersList.isEmpty()) {
			return ((BodyBuilder) ResponseEntity.notFound())
					.body(new MessageResponse("No orders found for " + username + "!"));
		}

		return ResponseEntity.ok(ordersList);
	}

	// PUT - Update order status
	@CrossOrigin(origins = "*", exposedHeaders = "**")
	@PreAuthorize("hasAnyRole('ROLE_RESTAURANT_OWNER', 'ROLE_ADMIN')")
	@PutMapping("/orders")
	public ResponseEntity<?> updateOrderStatus(@RequestBody OrdersResource orders) {

		logger.debug("Updating order status");

		if (orders == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Please provide order details."));
		}

		if (orders.getStatus() == null || orders.getUsername() == null || orders.getRestaurantName() == null
				|| orders.getFoodName() == null) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Please provide all order details to update the status."));
		}

		// Check if the status is "cancelling" or "rescheduling" and restrict to admin
		if ("cancelling".equalsIgnoreCase(orders.getStatus()) || "rescheduling".equalsIgnoreCase(orders.getStatus())) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null || !authentication.getAuthorities().stream()
					.anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(new MessageResponse("Only admins can update the status to cancelling or rescheduling."));
			}
		}

		if (Status.valueOf(orders.getStatus().toUpperCase()) == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Please provide a valid status."));
		}

		boolean response = ordersFacade.updateOrders(orders);
		if (!response) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Order status could not be updated!"));
		}

		return ResponseEntity.ok(new MessageResponse("Order status updated successfully!"));
	}

	// Get most popular restaurant
	@CrossOrigin(origins = "*", exposedHeaders = "**")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = "/popularRestaurant", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getMostPopularRestaurant(HttpServletRequest request) {

		logger.info("Get most poplar restaurant");

		RestaurantOwnerProfile restaurant = new RestaurantOwnerProfile();
		try {
			restaurant = ordersFacade.findMostPopularRestaurant();
		} catch (Exception e) {
			return ResponseEntity.internalServerError()
					.body(new MessageResponse("Error: Could not retireve popular restaurant!"));
		}

		return ResponseEntity.ok(restaurant);
	}

}
