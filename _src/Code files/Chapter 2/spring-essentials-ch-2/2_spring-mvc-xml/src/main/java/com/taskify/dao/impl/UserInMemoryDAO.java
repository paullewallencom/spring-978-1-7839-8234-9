package com.taskify.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.taskify.dao.UserDAO;
import com.taskify.domain.File;
import com.taskify.domain.User;

@Service
public class UserInMemoryDAO implements UserDAO {

	private List<User> userDataSource;

	public UserInMemoryDAO() {
		this.userDataSource = new ArrayList<User>();
	}

	public User findById(Long userId) {
		return userDataSource.parallelStream().filter(user -> user.getId().compareTo(userId) == 0).findAny()
				.orElse(null);
	}

	public User findByUserName(String userName) {
		return userDataSource.parallelStream().filter(user -> Objects.equals(user.getUserName(), userName)).findAny()
				.orElse(null);
	}

	private Long getIdSequenceNextVal() {
		return userDataSource.parallelStream().mapToLong(User::getId).max().orElse(0) + 1;
	}

	@Override
	public void createUser(User user) {
		user.setId(this.getIdSequenceNextVal());
		userDataSource.add(user);
	}

	@Override
	public void deleteUser(User user) {
		this.userDataSource.remove(user);
	}

	@Override
	public List<User> findAllUsers() {
		return this.userDataSource;
	}

	@Override
	public File addProfileImage(Long userId, String fileName) {
		User user = this.findById(userId);
		user.setProfileImage(new File(1000 + user.getId(), fileName));
		return user.getProfileImage();
	}

	@Override
	public void removeProfileImage(Long userId) {
		this.findById(userId).setProfileImage(null);
	}

}
