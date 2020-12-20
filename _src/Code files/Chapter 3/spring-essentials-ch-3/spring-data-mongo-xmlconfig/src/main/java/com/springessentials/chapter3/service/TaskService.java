package com.springessentials.chapter3.service;

import java.util.List;

import com.springessentials.chapter3.domain.Task;

public interface TaskService {
	Task createTask(Task task);

	Task createTask(String name, int priority, String createdByuserId, String assigneeUserId, String comments);

	Task findTaskById(String taskId);

	List<Task> findTasksByAssigneeId(String assigneeId);

	List<Task> findAllTasks();

	int findAllTasksCount();

	List<Task> findAllOpenTasks();

	List<Task> findAllCompletedTasks();

	int findAllOpenTasksCount();

	int findAllCompletedTasksCount();

	List<Task> findTasksByAssigneeUserName(String assigneeUserName);

	List<Task> findOpenTasksByAssigneeId(String assigneeId);

	List<Task> findOpenTasksByAssigneeUserName(String assigneeUserName);

	List<Task> findCompletedTasksByAssigneeId(String assigneeId);

	List<Task> findCompletedTasksByAssigneeUserName(String assigneeUserName);

	void completeTask(String taskId, String comments, String userId);

	void reassignTask(String taskId, String comments, String assigneeId);

	void deleteTask(String taskId);
	
}
