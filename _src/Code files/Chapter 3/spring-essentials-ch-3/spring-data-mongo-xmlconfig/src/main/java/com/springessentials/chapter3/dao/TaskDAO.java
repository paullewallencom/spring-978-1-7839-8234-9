package com.springessentials.chapter3.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.springessentials.chapter3.domain.Task;

public interface TaskDAO extends MongoRepository<Task, String>{
	
	List<Task> findByAssigneeId(String assigneeId);
	
	List<Task> findByAssigneeUserName(String userName);
	
	long count(); 
	
	List<Task> findByStatus(String status);
	
//	@Query("select t from Task t where status = 'Open'")
	@Query("{ 'status' : 'Open' }")
	List<Task> findOpenTasks();
	
//	@Query("select t from Task t where status = 'Complete'")
	@Query("{ 'status' : 'Complete' }")
	List<Task> findCompletedTasks();
	
//	@Query("select count(t) from Task t where status = 'Open'")
	@Query(value = "{ 'status' : 'Open' }", count = true)
	int findAllOpenTasksCount();
	
//	@Query("select count(t) from Task t where status = 'Complete'")
	@Query(value = "{ 'status' : 'Complete' }", count = true)
	int findAllCompletedTasksCount();
	
//	@Query("select t from Task t where status = 'Open' and assignee.id = ?1")
	@Query(value = "{ 'status' : 'Open', assignee.id: ?0 }")
	List<Task> findOpenTasksByAssigneeId(String assigneeId);
	
//	@Query("select t from Task t where status = 'Open' and assignee.userName = ?1")
	@Query(value = "{ 'status' : 'Open', assignee.userName: ?0 }")
	List<Task> findOpenTasksByAssigneeUserName(String userName);
	
//	@Query("select t from Task t where status = 'Complete' and assignee.id = ?1")
	@Query(value = "{ 'status' : 'Complete', assignee.id: ?0 }")
	List<Task> findCompletedTasksByAssigneeId(String assigneeId);
	
//	@Query("select t from Task t where status = 'Complete' and assignee.userName = ?1")
	@Query(value = "{ 'status' : 'Open', assignee.userName: ?0 }")
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
