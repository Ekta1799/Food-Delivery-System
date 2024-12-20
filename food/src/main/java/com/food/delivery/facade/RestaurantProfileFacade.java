package com.food.delivery.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.food.delivery.model.Menu;
import com.food.delivery.model.RestaurantOwnerProfile;
import com.food.delivery.model.User;
import com.food.delivery.pojo.MenuResource;
import com.food.delivery.pojo.RestaurantProfileResource;
import com.food.delivery.repository.RestaurantProfileRepository;
import com.food.delivery.repository.UserRepository;
import com.food.delivery.services.MenuService;
import com.food.delivery.services.RestaurantProfileService;

@Component
public class RestaurantProfileFacade {

	private static final Logger logger = LoggerFactory.getLogger(CustomerProfileFacade.class);

	@Autowired
	private RestaurantProfileService service;

	@Autowired
	RestaurantProfileRepository restaurantProfileRepo;

	@Autowired
	UserRepository userRepository;

	@Autowired
	private MenuService menuService;

	public void addRestaurantProfile(RestaurantProfileResource restaurantResource) {

		RestaurantOwnerProfile restaurant = new RestaurantOwnerProfile();
		restaurant.setFirstname(restaurantResource.getFirstname());
		restaurant.setLastname(restaurantResource.getLastname());
		restaurant.setUsername(restaurantResource.getUsername());
		restaurant.setAddress(restaurantResource.getAddress());
		restaurant.setPhone_no(restaurantResource.getPhone_no());
		restaurant.setRestaurant_name(restaurantResource.getRestaurant_name());
		restaurant.setHours_of_operation(restaurantResource.getHours_of_operation());

//		Long userId = userRepository.userByUsername(book.getUsername());
//		bookModel.setUser_id(userId);
//		logger.debug("user id = "+userId);

		service.addRestaurantProfile(restaurant);

	}

	public RestaurantProfileResource getRestaurantProfile(String firstName) {

		RestaurantOwnerProfile restaurantProfile = new RestaurantOwnerProfile();

		try {
			restaurantProfile = service.getRestaurantProfile(firstName);
		} catch (Exception e) {
			throw e;
		}

		RestaurantProfileResource restaurantProfileResource = convertDTOtoResource(restaurantProfile);

		return restaurantProfileResource;

	}

	private RestaurantProfileResource convertDTOtoResource(RestaurantOwnerProfile restaurant) {
		RestaurantProfileResource restaurantProfileResource = new RestaurantProfileResource();

		restaurantProfileResource.setFirstname(restaurant.getFirstname());
		restaurantProfileResource.setLastname(restaurant.getLastname());
		restaurantProfileResource.setAddress(restaurant.getAddress());
		restaurantProfileResource.setUsername(restaurant.getUsername());
		restaurantProfileResource.setRestaurant_name(restaurant.getRestaurant_name());
		restaurantProfileResource.setHours_of_operation(restaurant.getHours_of_operation());
		restaurantProfileResource.setPhone_no(restaurant.getPhone_no());

		return restaurantProfileResource;
	}

	public boolean updateRestaurantProfile(RestaurantProfileResource restaurantProfileResource) {

		String username = restaurantProfileResource.getUsername();
		String firstname = restaurantProfileResource.getFirstname();
		String lastname = restaurantProfileResource.getLastname();
		String phoneNo = restaurantProfileResource.getPhone_no();
		String address = restaurantProfileResource.getAddress();
		String restaurant = restaurantProfileResource.getRestaurant_name();
		int hrs = restaurantProfileResource.getHours_of_operation();

		Optional<User> userData = userRepository.findByUsername(username);
		User userVal = userData.get();

		if (restaurantProfileRepo.existsByUserId(userVal.getId())) {

			Optional<RestaurantOwnerProfile> restrnt = restaurantProfileRepo.findByUsername(username);
			RestaurantOwnerProfile restrnt1 = restrnt.get();

			try {

				restaurantProfileRepo.updateRestaurantProfile(restrnt1.getUser_id(), firstname, lastname, phoneNo,
						address, restaurant, hrs);
			} catch (Exception e) {
				logger.error("Could not update restaurant owner's profile : {}", e);
				return false;
			}

		} else {
			logger.error("Restaurant owner's profile does not exist for ID: " + userVal.getId());
			return false;
		}
		return true;
	}

	public List<MenuResource> getRestaurantList(String food_item, Boolean veg, String cuisine) {

		List<String> restaurantNames = restaurantProfileRepo.findAllRestaurants();

		MenuResource menuResource = new MenuResource();
		List<MenuResource> restaurants = new ArrayList<MenuResource>();
		for (String restaurant_name : restaurantNames) {
			Long restaurant_id = restaurantProfileRepo.getRestaurantIdByRestaurantName(restaurant_name);

			Menu menu = menuService.getMenuByRestaurantId(restaurant_id, food_item, veg, cuisine);

			menuResource.setFood_item(menu.getFood_item());
			menuResource.setPrice(menu.getPrice());
			menuResource.setCuisine(menu.getCuisine());
			menuResource.setVeg(menu.isVeg());
			menuResource.setRestaurant_name(restaurant_name);

			restaurants.add(menuResource);
		}

		return restaurants;

	}

	public boolean addMenu(List<MenuResource> menuResource) {

		for (MenuResource menuRes : menuResource) {

			Menu menu = new Menu();

			menu.setFood_item(menuRes.getFood_item());
			menu.setCuisine(menuRes.getCuisine());
			menu.setPrice(menuRes.getPrice());
			menu.setVeg(menuRes.isVeg());

			menu.setRestaurant_id(null);
			
			Long restuarantId = null;
			try {
				restuarantId = restaurantProfileRepo.getRestaurantIdByRestaurantName(menuRes.getRestaurant_name());
			} catch (Exception e) {
				return false;
			}
			
			menu.setRestaurant_id(restuarantId);

			menuService.addMenu(menu);
		}

		return true;
	}
}
