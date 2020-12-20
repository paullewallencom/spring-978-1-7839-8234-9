package com.taskify.service;

import java.util.List;

import com.taskify.domain.Task;

public interface TaskService {
	Task createTask(Task task);

	Task createTask(String name, int priority, Long createdByuserId, Long assigneeUserId, String comments);

	Task findTaskById(Long taskId);

	List<Task> findTasksByAssignee(Long assigneeId);

	List<Task> findAllTasks();

	int findAllTasksCount();

	List<Task> findAllOpenTasks();

	List<Task> findAllClosedTasks();

	int findAllOpenTasksCount();

	List<Task> findTasksByAssignee(String assigneeUserName);

	List<Task> findOpenTasksByAssignee(Long assigneeId);

	List<Task> findOpenTasksByAssignee(String assigneeUserName);

	List<Task> findClosedTasksByAssignee(Long assigneeId);

	List<Task> findClosedTasksByAssignee(String assigneeUserName);

	void completeTask(Long taskId, String comments, Long userId);

	void reassignTask(Long taskId, String comments, Long assigneeId);

	void deleteTask(Long taskId);

	void addFile(Long taskId, String fileName);

	void deleteFile(Long taskId, Long fileId);

	void deleteAllFiles(Long taskId);
	
}
