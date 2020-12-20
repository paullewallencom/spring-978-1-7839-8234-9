package com.springessentialsbook.chapter1.boot;

import com.springessentialsbook.chapter1.service.TaskService;
import com.springessentialsbook.chapter1.service.UserService;


public class DataLoader {
	
	private final UserService userService;
	private final TaskService taskService;

	
	public DataLoader(UserService userService, TaskService taskService) {
		super();
		this.userService = userService;
		this.taskService = taskService;
	}


	public void run() {
		this.loadUsers();
		this.loadTasks();
	}
	
	private void loadUsers() {
		this.userService.createNewUser("Shameer Kunjumohamed", "shameer", "n0thinG");
		this.userService.createNewUser("Hamidreza Sattari", "hamid", "1rrelev@nt");
	}
	
	private void loadTasks() {
		
	}

}
