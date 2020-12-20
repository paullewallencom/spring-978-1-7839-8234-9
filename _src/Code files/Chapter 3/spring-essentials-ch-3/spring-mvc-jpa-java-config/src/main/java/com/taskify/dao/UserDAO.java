package com.taskify.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskify.domain.User;

public interface UserDAO extends JpaRepository<User, Long>{
	
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
