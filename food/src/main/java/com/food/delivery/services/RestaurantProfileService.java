package com.food.delivery.services;

import com.food.delivery.model.RestaurantOwnerProfile;

public interface RestaurantProfileService {

	public void addRestaurantProfile(RestaurantOwnerProfile restaurant);

	public RestaurantOwnerProfile getRestaurantProfile(String firstname);
}