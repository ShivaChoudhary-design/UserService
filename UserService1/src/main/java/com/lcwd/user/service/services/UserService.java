package com.lcwd.user.service.services;

import java.util.List;

import com.lcwd.user.service.entities.User;

public interface UserService {
	//create
	User saveUser(User user);
	
	//get All User
	List<User> getAllUser();
	
	//get single user of giver userId
	User getUser(String userId);
	

}
