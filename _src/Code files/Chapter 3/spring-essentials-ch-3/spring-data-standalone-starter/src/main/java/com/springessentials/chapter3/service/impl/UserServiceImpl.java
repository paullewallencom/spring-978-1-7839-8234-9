package com.springessentials.chapter3.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springessentials.chapter3.dao.UserDAO;
import com.springessentials.chapter3.domain.File;
import com.springessentials.chapter3.domain.User;
import com.springessentials.chapter3.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
//	@Qualifier(value = "UserNamedParameterJdbcDAO")
//	@Qualifier("UserSimpleJdbcInsertDAO")
//	@Qualifier(value = "UserJdbcDAO")
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
	public void createUsers(List<User> users) {
		this.userDAO.createUsers(users);
	}

	@Override
	public void updateUser(User user) {
		User existingUser = this.userDAO.findById(user.getId());
		if (existingUser == null) {
			throw new UnsupportedOperationException("No user found with id, " + user.getId());
		}
		this.userDAO.updateUser(user.getId(), user);
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
