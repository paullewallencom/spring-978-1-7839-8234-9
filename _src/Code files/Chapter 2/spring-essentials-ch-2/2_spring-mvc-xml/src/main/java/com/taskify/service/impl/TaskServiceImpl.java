package com.taskify.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.taskify.dao.TaskDAO;
import com.taskify.domain.Task;
import com.taskify.service.TaskService;
import com.taskify.service.UserService;

@Service("AnnotatedTaskService")
@Primary
public class TaskServiceImpl implements TaskService {

	private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

	// @Autowired
	// private UserPreferences userPreferences;

	@Autowired
	private UserService userService;

	@Autowired(required = true)
	@Qualifier("taskDAO")
	private TaskDAO taskDAO;

	public TaskServiceImpl() {
		logger.debug("SimpleTaskService instantiated");
	}

	@Autowired
	public TaskServiceImpl(@Qualifier("userService") UserService userService, @Qualifier("taskDAO") TaskDAO taskDAO) {
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

	public List<Task> findAllClosedTasks() {
		return taskDAO.findAllClosedTasks();
	}

	@Override
	public int findAllOpenTasksCount() {
		return this.taskDAO.findAllOpenTasksCount();
	}

	public List<Task> findOpenTasksByAssignee(Long assignee) {
		return taskDAO.findOpenTasksByAssignee(assignee);
	}

	public List<Task> findOpenTasksByAssignee(String assigneeUserName) {
		return taskDAO.findOpenTasksByAssignee(assigneeUserName);
	}

	public List<Task> findClosedTasksByAssignee(Long assignee) {
		return taskDAO.findClosedTasksByAssignee(assignee);
	}

	public List<Task> findClosedTasksByAssignee(String assigneeUserName) {
		return taskDAO.findClosedTasksByAssignee(assigneeUserName);
	}

	public void completeTask(Long taskId, String comments, Long user) {
		Task task = taskDAO.findById(taskId);
		if (task.getAssignee().getId() != user) {
			throw new UnsupportedOperationException("This task is not assigned to this user");
		}
		task.setStatus("Complete");
		task.setCompletedDate(new Date());
		task.setComments(comments);

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

	@Override
	public void addFile(Long taskId, String fileName) {
		taskDAO.addFile(taskId, fileName);
	}

	@Override
	public void deleteFile(Long taskId, Long fileId) {
		taskDAO.deleteFile(taskId, fileId);
	}

	@Override
	public void deleteAllFiles(Long taskId) {
		taskDAO.deleteAllFiles(taskId);
	}

}
