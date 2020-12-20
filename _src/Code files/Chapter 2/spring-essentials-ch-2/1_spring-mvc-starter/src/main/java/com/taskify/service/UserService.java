package com.taskify.service;

import com.taskify.domain.User;

public interface UserService {
	User findById(int userId);
	User findByUserName(String userName);
	User createNewUser(String name, String userName, String password);
}
