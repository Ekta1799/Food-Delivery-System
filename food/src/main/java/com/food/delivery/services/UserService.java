package com.food.delivery.services;

import com.food.delivery.model.User;

public interface UserService {
	
	public User saveUser(User user);
	
	public User findUserByUsername(String username);
	
	public Boolean existsByUsername(String username);
	
	public void updatePassword(String password, String username);

}
