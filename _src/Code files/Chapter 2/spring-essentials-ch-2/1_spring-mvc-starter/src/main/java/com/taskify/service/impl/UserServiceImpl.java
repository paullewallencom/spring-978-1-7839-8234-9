package com.taskify.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskify.dao.UserDAO;
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

	public User findById(int userId) {
		return this.userDAO.findById(userId);
	}

	public User findByUserName(String userName) {
		return this.userDAO.findByUserName(userName);
	}

	public User createNewUser(String name, String userName, String password) {
		User newUser = new User(-1, name, userName, password);
		this.userDAO.createUser(newUser);
		logger.debug("New user created successfully: " + newUser);
		return newUser;
	}

}
