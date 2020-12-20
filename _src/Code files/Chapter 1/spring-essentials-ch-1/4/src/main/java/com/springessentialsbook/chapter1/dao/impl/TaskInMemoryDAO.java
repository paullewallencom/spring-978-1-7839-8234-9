package com.springessentialsbook.chapter1.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.springessentialsbook.chapter1.dao.TaskDAO;
import com.springessentialsbook.chapter1.domain.Task;

@Repository("taskDAO")
public class TaskInMemoryDAO implements TaskDAO {

	private List<Task> taskDataSource;

	public TaskInMemoryDAO() {
		this.taskDataSource = new ArrayList<Task>();
	}

	private int getIdSequenceNextVal() {
		return taskDataSource.parallelStream().mapToInt(Task::getId).max()
				.orElse(0) + 1;
	}

	@Override
	public void createTask(Task task) {
		task.setId(getIdSequenceNextVal());
		task.setCreatedDate(new Date());
		this.taskDataSource.add(task);
	}

	@Override
	public Task findById(int taskId) {
		return taskDataSource.parallelStream()
				.filter(task -> task.getId() == taskId).findAny().orElse(null);
	}

	@Override
	public List<Task> findByAssignee(int assigneeId) {
		return taskDataSource
				.parallelStream()
				.filter(task -> Objects.nonNull(task.getAssignee())
						&& Objects.equals(task.getAssignee().getId(),
								assigneeId)).collect(Collectors.toList());
	}

	@Override
	public List<Task> findByAssignee(String assigneeUserName) {
		return taskDataSource
				.parallelStream()
				.filter(task -> Objects.nonNull(task.getAssignee())
						&& Objects.equals(task.getAssignee().getUserName(),
								assigneeUserName)).collect(Collectors.toList());
	}

	@Override
	public List<Task> findAllOpenTasks() {
		return taskDataSource.parallelStream()
				.filter(task -> Objects.equals(task.getStatus(), "Open"))
				.collect(Collectors.toList());
	}

	@Override
	public List<Task> findOpenTasksByAssignee(int assigneeId) {
		return taskDataSource
				.parallelStream()
				.filter(task -> Objects.equals(task.getStatus(), "Open")
						&& Objects.nonNull(task.getAssignee())
						&& Objects.equals(task.getAssignee().getId(),
								assigneeId)).collect(Collectors.toList());
	}

	@Override
	public List<Task> findOpenTasksByAssignee(String assigneeUserName) {
		return taskDataSource
				.parallelStream()
				.filter(task -> Objects.equals(task.getStatus(), "Open")
						&& Objects.nonNull(task.getAssignee())
						&& Objects.equals(task.getAssignee().getUserName(),
								assigneeUserName)).collect(Collectors.toList());
	}

}
