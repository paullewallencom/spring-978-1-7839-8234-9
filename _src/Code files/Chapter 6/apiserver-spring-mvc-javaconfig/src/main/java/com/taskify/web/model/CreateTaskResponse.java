package com.taskify.web.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.taskify.domain.Task;

@XmlRootElement
public class CreateTaskResponse implements Serializable {

	private Task task;

	public CreateTaskResponse() {
		super();
	}

	public CreateTaskResponse(Task task) {
		super();
		this.task = task;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@Override
	public String toString() {
		return "CreateTaskResponse [task=" + task + "]";
	}

}
