package com.food.delivery.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.food.delivery.model.DeliveryPersonnelProfile;

import jakarta.transaction.Transactional;

@Transactional
public interface DeliveryPersonnelProfileRepository extends JpaRepository<DeliveryPersonnelProfile, Long> {

	Optional<DeliveryPersonnelProfile> findByUsername(String username);

	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM DeliveryPersonnelProfile u WHERE u.user_id = :user_id")
	Boolean existsByUserId(@Param("user_id") Long user_id);

	@Modifying
	@Query(value = "UPDATE delivery_personnel_profile SET firstname = ?2 , lastname = ?3, phone_no = ?4, vehicle_type = ?5, availability = ?6 WHERE id = ?1", nativeQuery = true)
	void updateUserProfile(Long userId, String firstname, String lastname, String phone_no, String vehicle_type, boolean availability);

	@Query("Select c from DeliveryPersonnelProfile c WHERE c.firstname = :firstname")
	DeliveryPersonnelProfile getDeliveryPersonnelProfile(@Param("firstname") String firstname);

}
