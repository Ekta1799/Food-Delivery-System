package com.food.delivery.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.delivery.model.Menu;
import com.food.delivery.repository.MenuRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

	private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

	@Autowired
	MenuRepository menuRepo;

	public Menu getMenuByRestaurantId(Long restaurant_id, String food_item, Boolean veg, String cuisine) {

		Menu menu = new Menu();
		
		logger.debug("food_item :::::::::: " + food_item + " veg ::::: " + veg + " cuisine :::::: " + cuisine + " id :::::: " + restaurant_id);

		if (food_item != null || veg != null || cuisine != null) {
			menu = menuRepo.searchMenu(restaurant_id, food_item, veg, cuisine);
		} else {
			menu = menuRepo.getMenuByRestaurantId(restaurant_id);
		}
		return menu;

	}

	public void addMenu(Menu menu) {
		menuRepo.save(menu);
	}

}
