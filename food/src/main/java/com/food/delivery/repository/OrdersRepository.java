package com.food.delivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.food.delivery.model.Orders;

import jakarta.transaction.Transactional;

@Transactional
public interface OrdersRepository extends JpaRepository<Orders, Long> {

	@Query("Select o from Orders o WHERE o.customer_id = :customer_id")
	Long getOrdersByCustomerId(@Param("customer_id") Long customer_id);

	@Query("Select o from Orders o WHERE o.restaurant_id = :restaurant_id")
	List<Orders> getOrdersByRestaurantId(@Param("restaurant_id") Long restaurant_id);

	@Query("Select o from Orders o WHERE o.customer_id = :customer_id")
	List<Orders> getOrderByCustomer(@Param("customer_id") Long customer_id);
	
	@Query("Select o from Orders o WHERE o.customer_id = :customer_id AND o.status = :status")
	List<Orders> getOrderByCustomerBasedOnStatus(@Param("customer_id") Long customer_id, @Param("status") String status);

	@Modifying
	@Query(value = "UPDATE orders SET status = ?4 WHERE customer_id = ?1 AND restaurant_id = ?2 AND food_id = ?3 ", nativeQuery = true)
	void updateStatus(Long customer_id, Long restaurant_id, Long food_id, String status);
	
	@Query("SELECT o.restaurant_id FROM Orders o GROUP BY o.restaurant_id ORDER BY COUNT(o) DESC")
	Long findMostPopularRestaurant();
	
	@Query(value = "SELECT * FROM orders WHERE status = 'OUT_FOR_DELIVERY'", nativeQuery = true)
	List<Orders> getDeliveryOrder();
	
	@Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Orders r WHERE r.id = :id")
	boolean existsById(@Param("id") Long id);

}