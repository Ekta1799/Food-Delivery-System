package com.food.delivery.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.food.delivery.model.User;

import jakarta.transaction.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	
	@Query("SELECT u.id FROM User u WHERE u.username =:username")
	Long userByUsername(@Param("username")String username);
	
	@Query("SELECT u.username FROM User u WHERE u.id =:id")
	String userByUserId(@Param("id")Long id);

	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.username = :username")
	Boolean existsByUsername(@Param("username")String username);

	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email")
	Boolean existsByEmail(@Param("email")String email);
	
	@Modifying
    @Query(value = "UPDATE user SET password = ?1 WHERE username = ?2", nativeQuery = true)
    void updatePassword(String password, String username);
}
