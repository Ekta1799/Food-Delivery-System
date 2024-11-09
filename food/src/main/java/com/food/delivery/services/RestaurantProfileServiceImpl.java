package com.food.delivery.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.delivery.model.RestaurantOwnerProfile;
import com.food.delivery.repository.MenuRepository;
import com.food.delivery.repository.RestaurantProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurantProfileServiceImpl implements RestaurantProfileService {

	@Autowired
	RestaurantProfileRepository restaurantProfileRepository;

	@Autowired
	MenuRepository menuRepository;

	public void addRestaurantProfile(RestaurantOwnerProfile restaurant) {
		restaurantProfileRepository.save(restaurant);
	}

	public RestaurantOwnerProfile getRestaurantProfile(String firstname) {
		RestaurantOwnerProfile restaurantProfile = new RestaurantOwnerProfile();

		restaurantProfile = restaurantProfileRepository.getRestaurantProfile(firstname);

		return restaurantProfile;
	}

}
