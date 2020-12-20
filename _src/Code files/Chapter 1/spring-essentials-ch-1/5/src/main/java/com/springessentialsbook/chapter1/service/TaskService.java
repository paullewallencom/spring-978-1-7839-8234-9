package com.springessentialsbook.chapter1.service;

import java.util.List;

import com.springessentialsbook.chapter1.domain.Task;

public interface TaskService {
	Task createTask(String name, int priority, int createdByuserId, int assigneeUserId);
	Task findTaskById(int taskId);
	List<Task> findTasksByAssignee(int assigneeId);
	List<Task> findAllTasks();
	List<Task> findAllOpenTasks();
	List<Task> findTasksByAssignee(String assigneeUserName);
	List<Task> findOpenTasksByAssignee(int assigneeId);
	List<Task> findOpenTasksByAssignee(String assigneeUserName);
	
	void completeTask(int taskId, String comments, int user);
}
