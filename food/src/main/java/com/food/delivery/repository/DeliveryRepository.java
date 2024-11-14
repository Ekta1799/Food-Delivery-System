package com.food.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.food.delivery.model.Delivery;

import jakarta.transaction.Transactional;

@Transactional
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
	
	@Modifying
	@Query(value = "UPDATE delivery SET delivery_status = ?1 WHERE delivery_personnel_id = ?2", nativeQuery = true)
	void updateDeliveryStatus(String status, Long delivery_personnel_id);

}