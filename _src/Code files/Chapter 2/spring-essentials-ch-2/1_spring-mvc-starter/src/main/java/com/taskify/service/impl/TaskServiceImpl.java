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
import com.taskify.domain.TaskComment;
import com.taskify.service.TaskService;
import com.taskify.service.UserService;

@Service("AnnotatedTaskService")
@Primary
public class TaskServiceImpl implements TaskService {

	private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

//	@Autowired
//	private UserPreferences userPreferences;
	
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

	public Task createTask(String name, int priority, int createdByuserId, int assigneeUserId) {

		Task task = new Task(name, priority, "Open", userService.findById(createdByuserId), null, userService.findById(assigneeUserId));
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

	public List<Task> findAllTasks() {
		return taskDAO.findAllTasks();
	}

	@Override
	public int findAllTasksCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Task> findAllOpenTasks() {
		return taskDAO.findAllOpenTasks();
	}

	@Override
	public int findAllOpenTasksCount() {
		// TODO Auto-generated method stub
		return 0;
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
			throw new UnsupportedOperationException("This task is not assigned to this user");
		}
		task.setStatus("Complete");
		task.setCompletedDate(new Date());

		if (!StringUtils.isEmpty(comments)) {
			task.getComments().add(new TaskComment(task.getAssignee(), comments, task.getStatus()));
		}
	}

}
