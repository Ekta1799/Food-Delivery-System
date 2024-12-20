package com.food.delivery.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.food.delivery.model.Orders;
import com.food.delivery.model.RestaurantOwnerProfile;
import com.food.delivery.model.User;
import com.food.delivery.pojo.OrdersResource;
import com.food.delivery.repository.MenuRepository;
import com.food.delivery.repository.OrdersRepository;
import com.food.delivery.repository.RestaurantProfileRepository;
import com.food.delivery.repository.UserRepository;
import com.food.delivery.services.OrdersService;

@Component
public class OrdersFacade {

	private static final Logger logger = LoggerFactory.getLogger(OrdersFacade.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	RestaurantProfileRepository restaurantProfileRepo;

	@Autowired
	MenuRepository menuRepo;

	@Autowired
	OrdersService ordersService;

	@Autowired
	OrdersRepository ordersRepo;

	public void addOrders(OrdersResource orders) {

		Optional<User> userData = userRepository.findByUsername(orders.getUsername());
		User userVal = userData.get();

		Long customer_id = userVal.getId();

		Long restuarant_id = restaurantProfileRepo.getRestaurantIdByRestaurantName(orders.getRestaurantName());

		Long food_id = menuRepo.getFoodIdFromFoodName(orders.getFoodName());

		Orders orderModel = new Orders();
		orderModel.setCustomer_id(customer_id);
		orderModel.setRestaurant_id(restuarant_id);
		orderModel.setFood_id(food_id);
		orderModel.setFood_price(orders.getFood_price());
		orderModel.setNo_of_items(orders.getNoOfItems());

		ordersService.addOrders(orderModel);
	}

	public boolean updateOrders(OrdersResource orders) {

		Optional<User> userData = userRepository.findByUsername(orders.getUsername());
		User userVal = userData.get();

		Long customer_id = userVal.getId();

		Long restuarant_id = restaurantProfileRepo.getRestaurantIdByRestaurantName(orders.getRestaurantName());

		Long food_id = menuRepo.getFoodIdFromFoodName(orders.getFoodName());

		String status = orders.getStatus();

		try {
			ordersService.updateStatus(customer_id, restuarant_id, food_id, status);
		} catch (Exception e) {
			logger.debug("Exception : " + e);
		}

		return true;
	}

	public List<OrdersResource> getOrdersByRestaurantOwner(String restaurantName) {

		List<OrdersResource> ordersList = new ArrayList<OrdersResource>();
		Long restuarant_id = restaurantProfileRepo.getRestaurantIdByRestaurantName(restaurantName);

		List<Orders> orderModel = ordersService.getOrdersByRestaurantOwner(restuarant_id);

		for (Orders order : orderModel) {

			OrdersResource orders = new OrdersResource();

			String username = userRepository.userByUserId(order.getCustomer_id);

			String foodName = menuRepo.getFoodNameFromFoodId(order.getFood_id());

			String restaurant = restaurantProfileRepo.getRestaurantNameByRestaurantId(order.getRestaurant_id());

			orders.setUsername(username);
			orders.setFoodName(foodName);
			orders.setRestaurantName(restaurant);
			orders.setFood_price(order.getFood_price());
			orders.setStatus(order.getStatus());
			orders.setNoOfItems(order.getNo_of_items());

			ordersList.add(orders);
		}

		return ordersList;
	}

	public List<OrdersResource> getOrderStatusByCustomer(String username, String status) {

		List<OrdersResource> ordersList = new ArrayList<OrdersResource>();

		Optional<User> userData = userRepository.findByUsername(username);
		User userVal = userData.get();

		Long customer_id = userVal.getId();

		List<Orders> orderModel = ordersService.getOrderStatusByCustomer(customer_id, status);

		for (Orders order : orderModel) {

			OrdersResource orders = new OrdersResource();

			String user = userRepository.userByUserId(order.getCustomer_id);

			String foodName = menuRepo.getFoodNameFromFoodId(order.getFood_id());

			String restaurant = restaurantProfileRepo.getRestaurantNameByRestaurantId(order.getRestaurant_id());

			orders.setUsername(user);
			orders.setFoodName(foodName);
			orders.setRestaurantName(restaurant);
			orders.setFood_price(order.getFood_price());
			orders.setStatus(order.getStatus());

			ordersList.add(orders);
		}

		return ordersList;

	}

	public RestaurantOwnerProfile findMostPopularRestaurant() {

		RestaurantOwnerProfile restaurant = new RestaurantOwnerProfile();
		try {
			Long restaurantId = ordersRepo.findMostPopularRestaurant();

			restaurant = restaurantProfileRepo.getRestaurantProfileById(restaurantId);
		} catch (Exception e) {
			throw e;
		}

		return restaurant;
	}

}
