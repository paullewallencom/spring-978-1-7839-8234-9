package com.springessentialsbook.chapter1.dao;

import com.springessentialsbook.chapter1.domain.User;

public interface UserDAO {
	
	void createUser(User user);
	User findById(int userId);
	User findByUserName(String userName);

}
