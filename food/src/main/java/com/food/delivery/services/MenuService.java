package com.food.delivery.services;

import com.food.delivery.model.Menu;

public interface MenuService {

	public Menu getMenuByRestaurantId(Long restaurant_id, String food_item, Boolean veg, String cuisine);

	public void addMenu(Menu menu);
}
