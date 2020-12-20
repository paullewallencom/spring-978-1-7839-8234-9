package com.springessentialsbook.chapter1.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.springessentialsbook.chapter1.dao.TaskDAO;
import com.springessentialsbook.chapter1.domain.Task;
import com.springessentialsbook.chapter1.domain.TaskComment;
import com.springessentialsbook.chapter1.service.TaskService;
import com.springessentialsbook.chapter1.service.UserService;

public class SimpleTaskService implements TaskService {

	private static final Logger logger = LoggerFactory
			.getLogger(SimpleTaskService.class);

	private UserService userService;
	private TaskDAO taskDAO;

	public SimpleTaskService(UserService userService, TaskDAO taskDAO) {
		super();
		this.userService = userService;
		this.taskDAO = taskDAO;
	}

	public void init() {
		logger.debug(this.getClass().getName() + " started!");
	}

	public void cleanup() {
		logger.debug(this.getClass().getName() + " is about to destroy!");
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public TaskDAO getTaskDAO() {
		return taskDAO;
	}

	public void setTaskDAO(TaskDAO taskDAO) {
		this.taskDAO = taskDAO;
	}

	public Task createTask(String name, int priority, int createdByuserId,
			int assigneeUserId) {

		Task task = new Task(name, priority, "Open",
				userService.findById(createdByuserId), null,
				userService.findById(assigneeUserId));
		taskDAO.createTask(task);
		logger.info("Task created: " + task);
		return task;
	}

	public Task findTaskById(int taskId) {
		return taskDAO.findById(taskId);
	}

	public List<Task> findTasksByAssignee(int assigneeId) {
		return taskDAO.findByAssignee(assigneeId);
	}

	public List<Task> findTasksByAssignee(String assigneeUserName) {
		return taskDAO.findByAssignee(assigneeUserName);
	}

	public List<Task> findAllOpenTasks() {
		return taskDAO.findAllOpenTasks();
	}

	public List<Task> findOpenTasksByAssignee(int assignee) {
		return taskDAO.findOpenTasksByAssignee(assignee);
	}

	public List<Task> findOpenTasksByAssignee(String assigneeUserName) {
		return taskDAO.findOpenTasksByAssignee(assigneeUserName);
	}

	public void completeTask(int taskId, String comments, int user) {
		Task task = taskDAO.findById(taskId);
		if (task.getAssignee().getId() != user) {
			throw new UnsupportedOperationException(
					"This task is not assigned to this user");
		}
		task.setStatus("Complete");
		task.setCompletedDate(new Date());

		if (!StringUtils.isEmpty(comments)) {
			task.getComments().add(
					new TaskComment(task.getAssignee(), comments, task
							.getStatus()));
		}
	}

}
