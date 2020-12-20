package com.springessentials.chapter3.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springessentials.chapter3.domain.Task;

public interface TaskDAO extends JpaRepository<Task, Long>{
	
	List<Task> findByAssigneeId(Long assigneeId);
	
	List<Task> findByAssigneeUserName(String userName);
	
	long count(); 
	
	List<Task> findByStatus(String status);
	
	@Query("select t from Task t where status = 'Open'")
	List<Task> findOpenTasks();
	
	@Query("select t from Task t where status = 'Complete'")
	List<Task> findCompletedTasks();
	
	@Query("select count(t) from Task t where status = 'Open'")
	int findAllOpenTasksCount();
	
	@Query("select count(t) from Task t where status = 'Complete'")
	int findAllCompletedTasksCount();
	
	@Query("select t from Task t where status = 'Open' and assignee.id = ?1")
	List<Task> findOpenTasksByAssigneeId(Long assigneeId);
	
	@Query("select t from Task t where status = 'Open' and assignee.userName = ?1")
	List<Task> findOpenTasksByAssigneeUserName(String userName);
	
	@Query("select t from Task t where status = 'Complete' and assignee.id = ?1")
	List<Task> findCompletedTasksByAssigneeId(Long assigneeId);
	
	@Query("select t from Task t where status = 'Complete' and assignee.userName = ?1")
	List<Task> findCompletedTasksByAssigneeUserName(String userName);
	
	/*
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
	*/

}
