package com.food.delivery.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.food.delivery.model.CustomerProfile;

import jakarta.transaction.Transactional;

@Transactional
public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Long> {

	Optional<CustomerProfile> findByUsername(String username);

	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM CustomerProfile u WHERE u.user_id = :user_id")
	Boolean existsByUserId(@Param("user_id") Long user_id);

	@Modifying
	@Query(value = "UPDATE customer_profile SET firstname = ?2 , lastname = ?3, phone_no = ?4, address = ?5, payment_type = ?6 WHERE id = ?1", nativeQuery = true)
	void updateUserProfile(Long userId, String firstname, String lastname, String phone_no, String address,
			String payment);

	@Query("Select c from CustomerProfile c WHERE c.firstname = :firstname")
	CustomerProfile getCustomerProfile(@Param("firstname") String firstname);

}
