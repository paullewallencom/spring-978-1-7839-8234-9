package com.taskify.web.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CreateTaskRequest")
public class CreateTaskRequest implements Serializable {

	private String taskName;
	private int priority;
	private Long creatorId;
	private Long assigneeId;
	private String comments;

	public CreateTaskRequest() {
		super();
	}

	public CreateTaskRequest(String taskName, int priority, Long creatorId, Long assigneeId, String comments) {
		super();
		this.taskName = taskName;
		this.priority = priority;
		this.creatorId = creatorId;
		this.assigneeId = assigneeId;
		this.comments = comments;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Long getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(Long assigneeId) {
		this.assigneeId = assigneeId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "CreateTaskRequest [taskName=" + taskName + ", priority=" + priority + ", creatorId=" + creatorId
				+ ", assigneeId=" + assigneeId + ", comments=" + comments + "]";
	}

}
