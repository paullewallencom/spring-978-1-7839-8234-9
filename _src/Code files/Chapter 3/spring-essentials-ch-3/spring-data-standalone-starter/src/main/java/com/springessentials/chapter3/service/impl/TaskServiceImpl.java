package com.springessentials.chapter3.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
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
//@DependsOn(value = { "PostgresTaskJdbcDAO" })
@Transactional
public class TaskServiceImpl implements TaskService {

	private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

	// @Autowired
	// private UserPreferences userPreferences;

	@Autowired
	private UserService userService;

	 @Autowired(required = true)
	// @Qualifier("PostgresTaskJdbcDAO")
	private TaskDAO taskDAO;

	public TaskServiceImpl() {
		logger.debug("TaskServiceImpl instantiated");
	}
/*
	@Autowired
	public TaskServiceImpl(@Qualifier("userService") UserService userService,
			@Qualifier("PostgresTaskJdbcDAO") TaskDAO taskDAO) {
		super();
		this.userService = userService;
		this.taskDAO = taskDAO;
	}
*/
	@PostConstruct
	public void init() {
		logger.debug(this.getClass().getName() + " started!");
	}

	@PreDestroy
	public void cleanup() {
		logger.debug(this.getClass().getName() + " is about to destroy!");
	}

	public Task createTask(String name, int priority, Long createdByuserId, Long assigneeUserId, String comments) {

		Task task = new Task(name, priority, "Open", userService.findById(createdByuserId), null,
				userService.findById(assigneeUserId), comments);
		taskDAO.createTask(task);
		logger.info("Task created: " + task);
		return task;
	}

	public Task createTask(Task task) {
		if (StringUtils.isEmpty(task.getStatus()))
			task.setStatus("Open");
		taskDAO.createTask(task);
		logger.info("Task created: " + task);
		return task;
	}

	public Task findTaskById(Long taskId) {
		return taskDAO.findById(taskId);
	}

	public List<Task> findTasksByAssignee(Long assigneeId) {
		return taskDAO.findByAssignee(assigneeId);
	}

	public List<Task> findTasksByAssignee(String assigneeUserName) {
		return taskDAO.findByAssignee(assigneeUserName);
	}

	public List<Task> findAllTasks() {
		return taskDAO.findAllTasks();
	}

	@Override
	public int findAllTasksCount() {
		return this.taskDAO.findAllTasksCount();
	}

	public List<Task> findAllOpenTasks() {
		return taskDAO.findAllOpenTasks();
	}

	public List<Task> findAllCompletedTasks() {
		return taskDAO.findAllCompletedTasks();
	}

	@Override
	public int findAllOpenTasksCount() {
		return this.taskDAO.findAllOpenTasksCount();
	}

	@Override
	public int findAllCompletedTasksCount() {
		return this.taskDAO.findAllCompletedTasksCount();
	}

	public List<Task> findOpenTasksByAssignee(Long assignee) {
		return taskDAO.findOpenTasksByAssignee(assignee);
	}

	public List<Task> findOpenTasksByAssignee(String assigneeUserName) {
		return taskDAO.findOpenTasksByAssignee(assigneeUserName);
	}

	public List<Task> findCompletedTasksByAssignee(Long assignee) {
		return taskDAO.findCompletedTasksByAssignee(assignee);
	}

	public List<Task> findCompletedTasksByAssignee(String assigneeUserName) {
		return taskDAO.findCompletedTasksByAssignee(assigneeUserName);
	}

	public void completeTask(Long taskId, String comments, Long user) {
		Task task = taskDAO.findById(taskId);
		if (task.getAssignee().getId() != user) {
			throw new UnsupportedOperationException("This task is not assigned to this user");
		}
		task.setStatus("Complete");
		task.setCompletedDate(new Date());
		task.setComments(comments);

		taskDAO.updateTask(task);
	}

	@Override
	public void reassignTask(Long taskId, String comments, Long assigneeId) {
		Task task = taskDAO.findById(taskId);
		task.setAssignee(userService.findById(assigneeId));
		task.setStatus("Open");
		task.setCompletedDate(null);
		task.setComments(comments);

	}

	@Override
	public void deleteTask(Long taskId) {
		taskDAO.deleteTask(taskDAO.findById(taskId));
	}

}
