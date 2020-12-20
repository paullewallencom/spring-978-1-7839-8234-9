package com.springessentials.chapter3.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.springessentials.chapter3.domain.User;

public interface UserDAO extends MongoRepository<User, String>{
	
	User findByUserName(String userName);
//	void createUser(User user);
//	void createUsers(List<User> users);
//	User findById(Long userId);
//	User findByUserName(String userName);
//	void deleteUser(User user);
//	void updateUser(Long userId, User user);
//	List<User> findAllUsers();
//
//	File addProfileImage(Long userId, String fileName);
//	void removeProfileImage(Long userId);
}
