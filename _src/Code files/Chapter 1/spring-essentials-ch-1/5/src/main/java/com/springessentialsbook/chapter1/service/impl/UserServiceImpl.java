package com.springessentialsbook.chapter1.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.springessentialsbook.chapter1.dao.UserDAO;
import com.springessentialsbook.chapter1.domain.User;
import com.springessentialsbook.chapter1.service.UserService;

public class UserServiceImpl implements UserService, InitializingBean, DisposableBean {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private final UserDAO userDAO;

	public UserServiceImpl(final UserDAO userDAO) {
		this.userDAO = userDAO;
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

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.debug(this.getClass().getName() + ".afterPropertiesSet() invoked!");
		// Your initialization code goes here..
	}

	@Override
	public void destroy() throws Exception {
		logger.debug(this.getClass().getName() + ".destroy() invoked!");
		// Your cleanup code goes here..
	}

}
