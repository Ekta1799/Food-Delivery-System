package com.food.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.food.delivery.model.Menu;

import jakarta.transaction.Transactional;

@Transactional
public interface MenuRepository extends JpaRepository<Menu, Long> {

	@Query("Select m from Menu m WHERE m.restaurant_id = :restaurant_id")
	Menu getMenuByRestaurantId(@Param("restaurant_id") Long restaurant_id);
	
	@Query("Select m.id from Menu m WHERE m.food_item = :food_item")
	Long getFoodIdFromFoodName(@Param("food_item") String food_item);
	
	@Query("Select m from Menu m WHERE m.food_item = :food_item")
	Menu getMenuFromFoodName(@Param("food_item") String food_item);
	
	@Query("Select m.food_item from Menu m WHERE m.id = :id")
	String getFoodNameFromFoodId(@Param("id") Long id);
	
	@Query("SELECT m FROM Menu m " +
            "WHERE (m.restaurant_id = :restaurant_id) " +
            "AND (:food_item IS NULL OR m.food_item = :food_item) " +
            "AND (:veg IS NULL OR m.veg = :veg) " +
            "AND (:cuisine IS NULL OR m.cuisine = :cuisine)")
    Menu searchMenu(@Param("restaurant_id") Long restaurant_id,
                             @Param("food_item") String food_item,
                             @Param("veg") boolean veg,
                             @Param("cuisine") String cuisine);

}
