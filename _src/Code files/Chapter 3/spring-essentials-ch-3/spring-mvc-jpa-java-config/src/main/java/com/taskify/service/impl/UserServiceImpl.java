package com.taskify.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taskify.dao.FileDAO;
import com.taskify.dao.UserDAO;
import com.taskify.domain.File;
import com.taskify.domain.User;
import com.taskify.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private FileDAO fileDAO;

	public UserServiceImpl() {
		logger.debug("SimpleTaskService instantiated");
	}

	public User findById(Long userId) {
		return this.userDAO.findOne(userId);
	}

	public User findByUserName(String userName) {
		return this.userDAO.findByUserName(userName);
	}

	public User createNewUser(User user) {
		if (user.getProfileImage() != null) {
			this.fileDAO.save(user.getProfileImage());
		}
		this.userDAO.save(user);
		logger.debug("New user created successfully: " + user);
		return user;
	}

	@Override
	public void createUsers(List<User> users) {
		for (User user : users) {
			if (user.getProfileImage() != null) {
				this.fileDAO.save(user.getProfileImage());
			}
		}
		this.userDAO.save(users);
	}

	@Override
	public void updateUser(User user) {
		User existingUser = this.userDAO.findOne(user.getId());
		if (existingUser == null) {
			throw new UnsupportedOperationException("No user found with id, " + user.getId());
		}

		if (user.getProfileImage() != null) {
			this.fileDAO.save(user.getProfileImage());
		}

		existingUser.setName(user.getName());
		existingUser.setDateOfBirth(user.getDateOfBirth());
		existingUser.setPassword(user.getPassword());
		existingUser.setProfileImage(user.getProfileImage());
		existingUser.setUserName(user.getUserName());
		this.userDAO.save(existingUser);
	}

	@Override
	public void deleteUser(User user) {
		User existingUser = this.userDAO.findOne(user.getId());
		if (existingUser == null) {
			throw new UnsupportedOperationException("No user found with id, " + user.getId());
		}
		this.userDAO.delete(existingUser);
	}

	@Override
	public List<User> findAllUsers() {
		return this.userDAO.findAll();
	}

	@Override
	public File addProfileImage(Long userId, String fileName) {
		User existingUser = this.userDAO.findOne(userId);
		if (existingUser == null) {
			throw new UnsupportedOperationException("No user found with id, " + userId);
		}
		existingUser.setProfileImage(new File(null, fileName));
		this.fileDAO.save(existingUser.getProfileImage());

		this.userDAO.save(existingUser);

		return existingUser.getProfileImage();
	}

	@Override
	public void deleteProfileImage(Long userId) {
		User existingUser = this.userDAO.findOne(userId);
		if (existingUser == null) {
			throw new UnsupportedOperationException("No user found with id, " + userId);
		}
		existingUser.setProfileImage(null);

		this.userDAO.save(existingUser);
	}

}
