package com.taskify.dao;

import java.util.List;

import com.taskify.domain.Task;

public interface TaskDAO {
	void createTask(Task task);

	Task findById(int taskId);

	List<Task> findByAssignee(int assigneeId);

	List<Task> findByAssignee(String assigneeUserName);

	List<Task> findAllTasks();

	int findAllTasksCount();

	List<Task> findAllOpenTasks();

	int findAllOpenTasksCount();

	List<Task> findOpenTasksByAssignee(int assigneeId);

	List<Task> findOpenTasksByAssignee(String assigneeUserName);

}
