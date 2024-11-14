package com.food.delivery.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.food.delivery.exception.GeneralException;
import com.food.delivery.model.Delivery;
import com.food.delivery.model.DeliveryPersonnelProfile;
import com.food.delivery.model.Orders;
import com.food.delivery.model.User;
import com.food.delivery.pojo.DeliveryPersonnelProfileResource;
import com.food.delivery.pojo.DeliveryResource;
import com.food.delivery.pojo.OrdersResource;
import com.food.delivery.repository.DeliveryPersonnelProfileRepository;
import com.food.delivery.repository.DeliveryRepository;
import com.food.delivery.repository.MenuRepository;
import com.food.delivery.repository.OrdersRepository;
import com.food.delivery.repository.RestaurantProfileRepository;
import com.food.delivery.repository.UserRepository;
import com.food.delivery.services.DeliveryPersonnelProfileService;
import com.food.delivery.services.OrdersService;

@Component
public class DeliveryPersonnelProfileFacade {

	private static final Logger logger = LoggerFactory.getLogger(DeliveryPersonnelProfileFacade.class);

	@Autowired
	private DeliveryPersonnelProfileService service;

	@Autowired
	UserRepository userRepository;

	@Autowired
	DeliveryPersonnelProfileRepository repo;

	@Autowired
	RestaurantProfileRepository restaurantProfileRepo;

	@Autowired
	MenuRepository menuRepo;

	@Autowired
	OrdersService ordersService;

	@Autowired
	DeliveryRepository deliveryRepo;

	@Autowired
	OrdersRepository ordersRepo;

	public void addDeliveryPersonnelProfile(DeliveryPersonnelProfileResource deliveryPersonnelProfileResource) {

		DeliveryPersonnelProfile deliveryPersonnel = new DeliveryPersonnelProfile();
		deliveryPersonnel.setFirstname(deliveryPersonnelProfileResource.getFirstname());
		deliveryPersonnel.setLastname(deliveryPersonnelProfileResource.getLastname());
		deliveryPersonnel.setUsername(deliveryPersonnelProfileResource.getUsername());
		deliveryPersonnel.setVehicle_type(deliveryPersonnelProfileResource.getVehicle_type());
		deliveryPersonnel.setPhone_no(deliveryPersonnelProfileResource.getPhone_no());
		deliveryPersonnel.setAvailability(deliveryPersonnelProfileResource.isAvailability());

		service.addDeliveryPersonnelProfile(deliveryPersonnel);

	}

	public DeliveryPersonnelProfileResource getDeliveryPersonnelProfile(String firstName) {

		DeliveryPersonnelProfile deliveryPersonnel = new DeliveryPersonnelProfile();

		try {
			deliveryPersonnel = service.getDeliveryPersonnelProfile(firstName);
		} catch (Exception e) {
			throw e;
		}

		DeliveryPersonnelProfileResource deliveryPersonnelResource = convertDTOtoResource(deliveryPersonnel);

		return deliveryPersonnelResource;

	}

	private DeliveryPersonnelProfileResource convertDTOtoResource(DeliveryPersonnelProfile deliveryPersonnel) {
		DeliveryPersonnelProfileResource deliveryPersonnelResource = new DeliveryPersonnelProfileResource();

		deliveryPersonnelResource.setFirstname(deliveryPersonnel.getFirstname());
		deliveryPersonnelResource.setLastname(deliveryPersonnel.getLastname());
		deliveryPersonnelResource.setUsername(deliveryPersonnel.getUsername());
		deliveryPersonnelResource.setVehicle_type(deliveryPersonnel.getVehicle_type());
		deliveryPersonnelResource.setPhone_no(deliveryPersonnel.getPhone_no());
		deliveryPersonnelResource.setAvailablity(deliveryPersonnel.isAvailability());

		return deliveryPersonnelResource;
	}

	public boolean updateDeliveryPersonnelProfile(DeliveryPersonnelProfileResource deliveryPersonnelProfileResource) {

		String username = deliveryPersonnelProfileResource.getUsername();
		String firstname = deliveryPersonnelProfileResource.getFirstname();
		String lastname = deliveryPersonnelProfileResource.getLastname();
		String phoneNo = deliveryPersonnelProfileResource.getPhone_no();
		String vehicleType = deliveryPersonnelProfileResource.getVehicle_type();
		boolean availability = deliveryPersonnelProfileResource.isAvailability();

		Optional<User> userData = userRepository.findByUsername(username);
		User userVal = userData.get();

		if (repo.existsByUserId(userVal.getId())) {

			Optional<DeliveryPersonnelProfile> delivery = repo.findByUsername(username);
			DeliveryPersonnelProfile delivery1 = delivery.get();

			try {

				repo.updateUserProfile(delivery1.getId(), firstname, lastname, phoneNo, vehicleType, availability);
			} catch (Exception e) {
				logger.error("Could not update delivery personnel profile : {}", e);
				return false;
			}

		} else {
			logger.error("Delivery personnel profile does not exist for user ID: " + userVal.getId());
			return false;
		}
		return true;
	}

	public List<OrdersResource> getDeliveryOrders() {

		List<OrdersResource> ordersList = new ArrayList<OrdersResource>();

		List<Orders> orderModel = ordersService.getDeliveryOrders();

		for (Orders order : orderModel) {

			OrdersResource orders = new OrdersResource();

			String username = userRepository.userByUserId(order.getId());

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

	public void addDeliveryDetails(DeliveryResource deliveryResource) {

		Delivery delivery = new Delivery();

		Optional<User> userData = userRepository.findByUsername(deliveryResource.getUsername());
		User userVal = userData.get();

		if (!ordersRepo.existsById(deliveryResource.getOrderId())) {
			throw new GeneralException("Order id does not exists", HttpStatus.BAD_REQUEST);
		}

			delivery.setDelivery_personnel_id(userVal.getId());
		delivery.setOrder_id(deliveryResource.getOrderId());
		delivery.setDelivery_status(deliveryResource.getDelivery_status());

		service.addDeliveryDetails(delivery);

	}

	public void updateDeliveryStatus(String status, Long deliveryPersonnelId) {

		logger.debug("++++ delivery personnel id +++++++ " + deliveryPersonnelId);

		deliveryRepo.updateDeliveryStatus(status, deliveryPersonnelId);

	}

}
