package com.taskify.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskify.dao.UserDAO;
import com.taskify.domain.File;
import com.taskify.domain.User;
import com.taskify.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDAO userDAO;

	public UserServiceImpl() {
		logger.debug("SimpleTaskService instantiated");
	}

	public User findById(Long userId) {
		return this.userDAO.findById(userId);
	}

	public User findByUserName(String userName) {
		return this.userDAO.findByUserName(userName);
	}

	public User createNewUser(User user) {
		user.setId(new Date().getTime());
		this.userDAO.createUser(user);
		logger.debug("New user created successfully: " + user);
		return user;
	}

	@Override
	public void updateUser(User user) {
		User existingUser = this.userDAO.findById(user.getId());
		if (existingUser == null) {
			throw new UnsupportedOperationException("No user found with id, " + user.getId());
		}
		existingUser.setDateOfBirth(user.getDateOfBirth());
		existingUser.setName(user.getName());
		existingUser.setPassword(user.getPassword());
		existingUser.setUserName(user.getUserName());
	}

	@Override
	public void deleteUser(User user) {
		User existingUser = this.userDAO.findById(user.getId());
		if (existingUser == null) {
			throw new UnsupportedOperationException("No user found with id, " + user.getId());
		}
		this.userDAO.deleteUser(existingUser);
	}

	@Override
	public List<User> findAllUsers() {
		return this.userDAO.findAllUsers();
	}

	@Override
	public File addProfileImage(Long userId, String fileName) {
		return this.userDAO.addProfileImage(userId, fileName);

	}

	@Override
	public void deleteProfileImage(Long userId) {
		this.userDAO.removeProfileImage(userId);
	}

}
