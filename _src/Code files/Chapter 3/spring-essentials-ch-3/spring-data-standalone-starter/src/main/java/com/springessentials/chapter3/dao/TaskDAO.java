package com.springessentials.chapter3.dao;

import java.util.List;

import com.springessentials.chapter3.domain.Task;

public interface TaskDAO {
	void createTask(Task task);

	Task findById(Long taskId);

	List<Task> findByAssignee(Long assigneeId);

	List<Task> findByAssignee(String assigneeUserName);

	List<Task> findAllTasks();

	int findAllTasksCount();

	List<Task> findAllOpenTasks();

	List<Task> findAllCompletedTasks();

	int findAllOpenTasksCount();

	int findAllCompletedTasksCount();

	List<Task> findOpenTasksByAssignee(Long assigneeId);

	List<Task> findOpenTasksByAssignee(String assigneeUserName);

	List<Task> findCompletedTasksByAssignee(Long assignee);

	List<Task> findCompletedTasksByAssignee(String assigneeUserName);

	void deleteTask(Task task);

	void updateTask(Task task);

}
