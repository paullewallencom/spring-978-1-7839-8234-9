package com.taskify.domain;

import java.util.Date;
import java.util.List;

public class Task {

	private Long id;
	private String name;
	private int priority;
	private String status;
	private User createdBy;
	private Date createdDate;
	private User assignee;
	private Date completedDate;
	
	private List<File> files;

	private String comments;

	public Task() {
	}

	public Task(String name, int priority, String status, User createdBy, Date createdDate, User assignee,
			String comments) {
		super();
		this.name = name;
		this.priority = priority;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.assignee = assignee;
		this.comments = comments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getAssignee() {
		return assignee;
	}

	public void setAssignee(User assignee) {
		this.assignee = assignee;
	}

	public Date getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", name=" + name + ", priority=" + priority + ", status=" + status + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", assignee=" + assignee + ", completedDate="
				+ completedDate + ", files=" + files + ", comments=" + comments + "]";
	}

}
