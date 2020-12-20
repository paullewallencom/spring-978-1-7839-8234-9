package com.taskify.web.controller;

import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.taskify.domain.User;
import com.taskify.service.UserService;
import com.taskify.web.model.CreateUserRequest;
import com.taskify.web.model.UpdateUserRequest;

/**
 * Handles requests for user related pages.
 */
@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	private static final String PROFILE_IMAGE_SAVE_LOCATION = "/tmp/taskify/profileImages";

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Callable<List<User>> listAllUsers() {

		return new Callable<List<User>>() {
			@Override
			public List<User> call() throws Exception {
				return userService.findAllUsers();
			}
		};
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Callable<User> createNewUser(@RequestBody CreateUserRequest request) {
		logger.info(">>>>>>>> Creatinge User, request - " + request);
		return new Callable<User>() {
			@Override
			public User call() throws Exception {
				return userService.createNewUser(request.getUser());
			}
		};
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Callable<User> updateUser(@PathVariable("id") Long id, @RequestBody UpdateUserRequest request) {
		logger.info(">>>>>>>> updateUser, request - " + request);
		return new Callable<User>() {
			@Override
			public User call() throws Exception {
				User existingUser = userService.findById(id);
				existingUser.setName(request.getUser().getName());
				existingUser.setPassword(request.getUser().getPassword());
				existingUser.setUserName(request.getUser().getUserName());
				userService.updateUser(existingUser);
				return existingUser;
			}
		};
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public Callable<User> getUser(@PathVariable("id") Long id) {
		return new Callable<User>() {
			@Override
			public User call() throws Exception {
				return userService.findById(id);
			}
		};
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public Callable<Void> deleteUser(@PathVariable("id") Long id) {
		return new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				userService.deleteUser(userService.findById(id));
				return null;
			}
		};
	}
}
