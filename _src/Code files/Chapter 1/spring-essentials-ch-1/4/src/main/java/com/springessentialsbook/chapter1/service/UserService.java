package com.springessentialsbook.chapter1.service;

import com.springessentialsbook.chapter1.domain.User;

public interface UserService {
	User findById(int userId);
	User findByUserName(String userName);
	User createNewUser(String name, String userName, String password);
}
