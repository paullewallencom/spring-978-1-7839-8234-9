package com.springessentials.chapter3.dao;

import java.util.List;

import com.springessentials.chapter3.domain.File;
import com.springessentials.chapter3.domain.User;

public interface UserDAO {
	
	void createUser(User user);
	void createUsers(List<User> users);
	User findById(Long userId);
	User findByUserName(String userName);
	void deleteUser(User user);
	void updateUser(Long userId, User user);
	List<User> findAllUsers();

	File addProfileImage(Long userId, String fileName);
	void removeProfileImage(Long userId);
}
