package com.springessentialsbook.chapter1.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.springessentialsbook.chapter1.dao.UserDAO;
import com.springessentialsbook.chapter1.domain.User;

@Service
public class UserInMemoryDAO implements UserDAO {

	private List<User> userDataSource;

	public UserInMemoryDAO() {
		this.userDataSource = new ArrayList<User>();
	}

	public User findById(int userId) {
		return userDataSource.parallelStream()
				.filter(user -> user.getId() == userId).findAny().orElse(null);
	}

	public User findByUserName(String userName) {
		return userDataSource.parallelStream()
				.filter(user -> Objects.equals(user.getUserName(), userName))
				.findAny().orElse(null);
	}

	private int getIdSequenceNextVal() {
		return userDataSource.parallelStream().mapToInt(User::getId).max()
				.orElse(0) + 1;
	}

	@Override
	public void createUser(User user) {
		user.setId(this.getIdSequenceNextVal());
		userDataSource.add(user);
	}

}
