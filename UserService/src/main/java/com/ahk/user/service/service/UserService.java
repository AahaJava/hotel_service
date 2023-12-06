package com.ahk.user.service.service;

import java.util.List;

import com.ahk.user.service.entities.User;

public interface UserService {
	
	User saveUser(User user);
	List<User> getAllUsers();
	User getUser(String userId);

}
