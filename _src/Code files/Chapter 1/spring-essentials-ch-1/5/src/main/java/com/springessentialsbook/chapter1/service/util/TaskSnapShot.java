package com.springessentialsbook.chapter1.service.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.springessentialsbook.chapter1.domain.Task;

@Component
@Scope("prototype")
public class TaskSnapShot {
	
	@Value("#{AnnotatedTaskService.findAllTasks().size()}")
	private String totalTasks;
	
	@Value("#{AnnotatedTaskService.findAllTasks()}")
	private List<Task> taskList;
	
	@Value("#{ new java.util.Date()}")
	private Date reportTime;
	
	@Value("#{AnnotatedTaskService.findAllTasks().?[assignee.userName == 'hamid']}")
	private List<Task> hamidTasks;
	
	@Value("#{AnnotatedTaskService.findAllTasks().?[status == 'Open']}")
	private List<Task> openTasks;
	
	public TaskSnapShot() {
		// TODO Auto-generated constructor stub
	}

	public String getTotalTasks() {
		return totalTasks;
	}

	public void setTotalTasks(String totalTasks) {
		this.totalTasks = totalTasks;
	}

	public List<Task> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
//
//	public Map<String, Task> getTaskAssigneeMap() {
//		return taskAssigneeMap;
//	}
//
//	public void setTaskAssigneeMap(Map<String, Task> taskAssigneeMap) {
//		this.taskAssigneeMap = taskAssigneeMap;
//	}

	public List<Task> getHamidTasks() {
		return hamidTasks;
	}

	public void setHamidTasks(List<Task> hamidTasks) {
		this.hamidTasks = hamidTasks;
	}

	public List<Task> getOpenTasks() {
		return openTasks;
	}

	public void setOpenTasks(List<Task> openTasks) {
		this.openTasks = openTasks;
	}

	@Override
	public String toString() {
		return "TaskSnapShot [totalTasks=" + totalTasks + ", taskList="
				+ taskList + ", reportTime=" + reportTime + ", hamidTasks="
				+ hamidTasks + ", openTasks=" + openTasks + "]";
	}

}
