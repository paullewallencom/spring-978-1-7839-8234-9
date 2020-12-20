package com.taskify.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.taskify.dao.TaskDAO;
import com.taskify.domain.File;
import com.taskify.domain.Task;

@Repository("taskDAO")
public class TaskInMemoryDAO implements TaskDAO {

	private List<Task> taskDataSource;

	public TaskInMemoryDAO() {
		this.taskDataSource = new ArrayList<Task>();
	}

	private long getIdSequenceNextVal() {
		return taskDataSource.parallelStream().mapToLong(Task::getId).max().orElse(0) + 1;
	}

	@Override
	public void createTask(Task task) {
		task.setId(getIdSequenceNextVal());
		task.setCreatedDate(new Date());
		this.taskDataSource.add(task);
	}

	@Override
	public Task findById(Long taskId) {
		return taskDataSource.parallelStream().filter(task -> task.getId() == taskId).findAny().orElse(null);
	}

	@Override
	public List<Task> findByAssignee(Long assigneeId) {
		return taskDataSource.parallelStream().filter(
				task -> Objects.nonNull(task.getAssignee()) && Objects.equals(task.getAssignee().getId(), assigneeId))
				.collect(Collectors.toList());
	}

	@Override
	public List<Task> findByAssignee(String assigneeUserName) {
		return taskDataSource.parallelStream()
				.filter(task -> Objects.nonNull(task.getAssignee())
						&& Objects.equals(task.getAssignee().getUserName(), assigneeUserName))
				.collect(Collectors.toList());
	}

	@Override
	public List<Task> findAllTasks() {
		return taskDataSource.parallelStream().collect(Collectors.toList());
	}

	@Override
	public int findAllTasksCount() {
		return taskDataSource.size();
	}

	@Override
	public List<Task> findAllOpenTasks() {
		return taskDataSource.parallelStream().filter(task -> Objects.equals(task.getStatus(), "Open"))
				.collect(Collectors.toList());
	}

	@Override
	public List<Task> findAllClosedTasks() {
		return taskDataSource.parallelStream().filter(task -> Objects.equals(task.getStatus(), "Closed"))
				.collect(Collectors.toList());
	}

	@Override
	public int findAllOpenTasksCount() {
		return this.findAllOpenTasks().size();
	}

	@Override
	public List<Task> findOpenTasksByAssignee(Long assigneeId) {
		return taskDataSource.parallelStream().filter(task -> Objects.equals(task.getStatus(), "Open")
				&& Objects.nonNull(task.getAssignee()) && Objects.equals(task.getAssignee().getId(), assigneeId))
				.collect(Collectors.toList());
	}

	@Override
	public List<Task> findOpenTasksByAssignee(String assigneeUserName) {
		return taskDataSource.parallelStream()
				.filter(task -> Objects.equals(task.getStatus(), "Open") && Objects.nonNull(task.getAssignee())
						&& Objects.equals(task.getAssignee().getUserName(), assigneeUserName))
				.collect(Collectors.toList());
	}

	@Override
	public List<Task> findClosedTasksByAssignee(Long assigneeId) {
		return taskDataSource.parallelStream().filter(task -> Objects.equals(task.getStatus(), "Closed")
				&& Objects.nonNull(task.getAssignee()) && Objects.equals(task.getAssignee().getId(), assigneeId))
				.collect(Collectors.toList());
	}

	@Override
	public List<Task> findClosedTasksByAssignee(String assigneeUserName) {
		return taskDataSource.parallelStream()
				.filter(task -> Objects.equals(task.getStatus(), "Closed") && Objects.nonNull(task.getAssignee())
						&& Objects.equals(task.getAssignee().getUserName(), assigneeUserName))
				.collect(Collectors.toList());
	}

	@Override
	public void deleteTask(Task task) {
		taskDataSource.remove(task);
	}

	@Override
	public void addFile(Long taskId, String fileName) {
		Task task = this.findById(taskId);
		if (task.getFiles() == null) {
			task.setFiles(new ArrayList<>(1));
		}

		task.getFiles().add(new File((task.getId() * 10) + (task.getFiles().size() + 1), fileName));
	}

	private File findFileById(Task task, Long fileId) {
		if (!CollectionUtils.isEmpty(task.getFiles())) {
			for (File file : task.getFiles()) {
				if (file.getId() == fileId)
					return file;

			}
		}
		return null;
	}

	@Override
	public void deleteFile(Long taskId, Long fileId) {
		Task task = this.findById(taskId);
		File file = findFileById(task, fileId);
		if (file != null)
			task.getFiles().remove(file);

	}

	@Override
	public void deleteAllFiles(Long taskId) {
		Task task = this.findById(taskId);
		if (!CollectionUtils.isEmpty(task.getFiles())) {
			task.getFiles().clear();
		}

	}

}
