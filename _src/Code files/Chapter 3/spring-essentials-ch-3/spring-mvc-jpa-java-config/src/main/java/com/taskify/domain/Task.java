package com.taskify.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tbl_task")
public class Task {

	@Id
	@SequenceGenerator(name = "SEQ_TASK", sequenceName = "SEQ_TASK", allocationSize = 1, initialValue=1001)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TASK")
	private Long id;

	@Column(name = "NAME", length = 500)
	private String name;

	@Column(name = "PRIORITY")
	private int priority;

	@Column(name = "STATUS")
	private String status;

	@ManyToOne(optional = true)
	@JoinColumn(name = "CREATED_USER_ID", referencedColumnName = "ID")
	private User createdBy;

	@Column(name = "CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@ManyToOne(optional = true)
	@JoinColumn(name = "ASSIGNEE_USER_ID", referencedColumnName = "ID")
	private User assignee;
	private Date completedDate;

	@Column(name = "COMMENTS")
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

	@Override
	public String toString() {
		return "Task [id=" + id + ", name=" + name + ", priority=" + priority + ", status=" + status + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", assignee=" + assignee + ", completedDate="
				+ completedDate + ", comments=" + comments + "]";
	}

}
