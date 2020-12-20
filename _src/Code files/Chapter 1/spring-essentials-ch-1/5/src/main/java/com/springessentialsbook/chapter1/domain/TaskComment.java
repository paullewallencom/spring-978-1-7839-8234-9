package com.springessentialsbook.chapter1.domain;

public class TaskComment {
	
	private User user;
	private String comment;
	private String status;

	public TaskComment() {
		// TODO Auto-generated constructor stub
	}

	public TaskComment(User user, String comment, String status) {
		super();
		this.user = user;
		this.comment = comment;
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "TaskComment [user=" + user + ", comment=" + comment
				+ ", status=" + status + "]";
	}

}
