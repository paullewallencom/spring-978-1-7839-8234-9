package com.springessentials.chapter3.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.springessentials.chapter3.dao.TaskDAO;
import com.springessentials.chapter3.domain.Task;
import com.springessentials.chapter3.service.TaskService;
import com.springessentials.chapter3.service.UserService;

@Service("AnnotatedTaskService")
@Primary
@Transactional
public class TaskServiceImpl implements TaskService {

	private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

	// @Autowired
	// private UserPreferences userPreferences;

	@Autowired
	private UserService userService;

	 @Autowired(required = true)
	private TaskDAO taskDAO;

	public TaskServiceImpl() {
		logger.debug("TaskServiceImpl instantiated");
	}

//	@Autowired
	public TaskServiceImpl(UserService userService, TaskDAO taskDAO) {
		super();
		this.userService = userService;
		this.taskDAO = taskDAO;
	}

	@PostConstruct
	public void init() {
		logger.debug(this.getClass().getName() + " started!");
	}

	@PreDestroy
	public void cleanup() {
		logger.debug(this.getClass().getName() + " is about to destroy!");
	}

	public Task createTask(String name, int priority, String createdByuserId, String assigneeUserId, String comments) {

		Task task = new Task(name, priority, "Open", userService.findById(createdByuserId), null,
				userService.findById(assigneeUserId), comments);
		taskDAO.save(task);
		logger.info("Task created: " + task);
		return task;
	}

	public Task createTask(Task task) {
		if (StringUtils.isEmpty(task.getStatus()))
			task.setStatus("Open");
		taskDAO.save(task);
		logger.info("Task created: " + task);
		return task;
	}

	public Task findTaskById(String taskId) {
		return taskDAO.findOne(taskId);
	}

	public List<Task> findTasksByAssigneeId(String assigneeId) {
		return taskDAO.findByAssigneeId(assigneeId);

	}

	public List<Task> findTasksByAssigneeUserName(String assigneeUserName) {
		return taskDAO.findByAssigneeUserName(assigneeUserName);

	}

	public List<Task> findAllTasks() {
		return taskDAO.findAll();

	}

	@Override
	public int findAllTasksCount() {
		return (int) this.taskDAO.count();

	}

	public List<Task> findAllOpenTasks() {
		return taskDAO.findOpenTasks();

	}

	public List<Task> findAllCompletedTasks() {
		return taskDAO.findCompletedTasks();

	}

	@Override
	public int findAllOpenTasksCount() {
		return this.taskDAO.findAllOpenTasksCount();

	}

	@Override
	public int findAllCompletedTasksCount() {
		return this.taskDAO.findAllCompletedTasksCount();

	}

	public List<Task> findOpenTasksByAssigneeId(String assignee) {
		return taskDAO.findOpenTasksByAssigneeId(assignee);

	}

	public List<Task> findOpenTasksByAssigneeUserName(String assigneeUserName) {
		return taskDAO.findOpenTasksByAssigneeUserName(assigneeUserName);

	}

	public List<Task> findCompletedTasksByAssigneeId(String assignee) {
		return taskDAO.findCompletedTasksByAssigneeId(assignee);

	}

	public List<Task> findCompletedTasksByAssigneeUserName(String assigneeUserName) {
		return taskDAO.findCompletedTasksByAssigneeUserName(assigneeUserName);

	}

	public void completeTask(String taskId, String comments, String userId) {
		Task task = taskDAO.findOne(taskId);
		if (!task.getAssignee().getId().equals(userId)) {
			throw new UnsupportedOperationException("This task is not assigned to this user");
		}
		task.setStatus("Complete");
		task.setCompletedDate(new Date());
		task.setComments(comments);

		taskDAO.save(task);
		// taskDAO.flush();
	}

	@Override
	public void reassignTask(String taskId, String comments, String assigneeId) {
		Task task = taskDAO.findOne(taskId);
		task.setAssignee(userService.findById(assigneeId));
		task.setStatus("Open");
		task.setCompletedDate(null);
		task.setComments(comments);

	}

	@Override
	public void deleteTask(String taskId) {
		taskDAO.delete(taskDAO.findOne(taskId));
	}

}
