package com.taskify.dao;

import com.taskify.domain.User;

public interface UserDAO {
	
	void createUser(User user);
	User findById(int userId);
	User findByUserName(String userName);

}
