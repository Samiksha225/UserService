package com.user.service.service;

import java.util.List;

import com.user.service.entities.User;

public interface UserService {

	User saveUser(User user);
	
	List<User> getAllUser();
	
	User getUser(int UserId);
	
}
