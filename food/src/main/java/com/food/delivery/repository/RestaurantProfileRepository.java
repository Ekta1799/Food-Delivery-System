package com.food.delivery.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.food.delivery.model.RestaurantOwnerProfile;

import jakarta.transaction.Transactional;

@Transactional
public interface RestaurantProfileRepository extends JpaRepository<RestaurantOwnerProfile, Long> {

	Optional<RestaurantOwnerProfile> findByUsername(String username);

	@Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM RestaurantOwnerProfile r WHERE r.user_id = :user_id")
	Boolean existsByUserId(@Param("user_id") Long user_id);

	@Modifying
	@Query(value = "UPDATE restaurant_profile SET firstname = ?2 , lastname = ?3, phone_no = ?4, address = ?5, restaurant_name = ?6, hours_of_operation = ?7 WHERE user_id = ?1", nativeQuery = true)
	void updateRestaurantProfile(Long user_id, String firstname, String lastname, String phone_no, String address,
			String restaurantname, int hours);

	@Query("Select r from RestaurantOwnerProfile r WHERE r.firstname = :firstname")
	RestaurantOwnerProfile getRestaurantProfile(@Param("firstname") String firstname);
	
	@Query("Select r from RestaurantOwnerProfile r WHERE r.id = :id")
	RestaurantOwnerProfile getRestaurantProfileById(@Param("id") Long id);

	@Query("Select r.restaurant_name from RestaurantOwnerProfile r")
	List<String> findAllRestaurants();

	@Query("Select r.id from RestaurantOwnerProfile r WHERE r.restaurant_name = :restaurant_name")
	Long getRestaurantIdByRestaurantName(@Param("restaurant_name") String restaurant_name);

	@Query("Select r.restaurant_name from RestaurantOwnerProfile r WHERE r.id = :id")
	String getRestaurantNameByRestaurantId(@Param("id") Long id);
}
