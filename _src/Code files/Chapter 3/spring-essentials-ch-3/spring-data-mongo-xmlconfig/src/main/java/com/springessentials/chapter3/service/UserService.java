package com.springessentials.chapter3.service;

import java.util.List;

import com.springessentials.chapter3.domain.File;
import com.springessentials.chapter3.domain.User;

public interface UserService {
	User findById(String userId);

	User findByUserName(String userName);

	User createNewUser(User user);

	void createUsers(List<User> users);

	void updateUser(User user);

	void deleteUser(User user);

	List<User> findAllUsers();

	File addProfileImage(String userId, String fileName);

	void deleteProfileImage(String userId);

}
