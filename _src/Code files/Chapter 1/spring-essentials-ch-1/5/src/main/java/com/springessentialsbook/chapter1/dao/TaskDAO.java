package com.springessentialsbook.chapter1.dao;

import java.util.List;

import com.springessentialsbook.chapter1.domain.Task;

public interface TaskDAO {
	void createTask(Task task);

	Task findById(int taskId);

	List<Task> findByAssignee(int assigneeId);

	List<Task> findByAssignee(String assigneeUserName);

	List<Task> findAllTasks();

	List<Task> findAllOpenTasks();

	List<Task> findOpenTasksByAssignee(int assigneeId);

	List<Task> findOpenTasksByAssignee(String assigneeUserName);

}
