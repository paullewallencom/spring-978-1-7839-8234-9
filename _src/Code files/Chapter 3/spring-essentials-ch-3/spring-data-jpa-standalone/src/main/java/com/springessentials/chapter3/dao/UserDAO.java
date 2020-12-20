package com.springessentials.chapter3.dao;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;

import com.springessentials.chapter3.domain.User;

public interface UserDAO extends JpaRepository<User, Long> {

	// Returns unique user with given user-name
	User findByUserName(String userName);

	// Returns a paginated list of users whose name starts with given value
	Page<User> findByNameStartsWith(String name, Pageable pageable);

	// Returns first 5 users whose name starts with given value,
	// order by name descending
	List<User> findTop5ByNameStartsWithOrderByNameDesc(String name);

	// Returns number of users whose birth date is before the given value
	Long countUsersDateOfBirthLessThan(Date dob);

	// Deletes the User of given id
	void deleteById(Long userId);

	// Asynchronously returns a list of users whose name contains with given value
	@Async
	Future<List<User>> findByNameContains(String name);

}
