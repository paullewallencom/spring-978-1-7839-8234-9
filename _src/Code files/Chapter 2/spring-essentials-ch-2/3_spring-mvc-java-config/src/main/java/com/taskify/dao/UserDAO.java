package com.taskify.dao;

import java.util.List;

import com.taskify.domain.File;
import com.taskify.domain.User;

public interface UserDAO {
	
	void createUser(User user);
	User findById(Long userId);
	User findByUserName(String userName);
	void deleteUser(User user);
	List<User> findAllUsers();

	File addProfileImage(Long userId, String fileName);
	void removeProfileImage(Long userId);
}
