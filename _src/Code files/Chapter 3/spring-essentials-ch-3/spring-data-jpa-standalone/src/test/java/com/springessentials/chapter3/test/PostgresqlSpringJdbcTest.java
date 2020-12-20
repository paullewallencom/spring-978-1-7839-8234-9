package com.springessentials.chapter3.test;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import com.springessentials.chapter3.domain.Task;
import com.springessentials.chapter3.domain.User;
import com.springessentials.chapter3.service.TaskService;
import com.springessentials.chapter3.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@ComponentScan(basePackages = {"com.springessentials.chapter3"})
@ContextConfiguration(locations = { "/test-context-postgres.xml" })
//@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = { "/db-scripts/postgresql/db-schema.sql",
//		"/db-scripts/postgresql/data.sql", "/db-scripts/postgresql/functions.sql"  })
//@SqlConfig(blockCommentEndDelimiter="/", separator="/")
public class PostgresqlSpringJdbcTest {
	private static final Logger logger = LoggerFactory.getLogger(PostgresqlSpringJdbcTest.class);

	@Resource(name = "postgresql-ds")
	private Properties pgDsProps;

	
	@Autowired
	private DataSource dataSource;

	@Autowired
	private UserService userService;

	@Autowired
	private TaskService taskService;
/*
	@Bean
	DataSource getDatasource() {
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource(pgDsProps.getProperty("url"));
		dataSource.setDriverClassName(pgDsProps.getProperty("driverClassName"));
		dataSource.setUsername(pgDsProps.getProperty("username"));
		dataSource.setPassword(pgDsProps.getProperty("password"));
		return dataSource;
//		return new DriverManagerDataSource(pgDsProps.getProperty("url"), pgDsProps);
//		return new DriverManagerDataSource(pgDsProps.getProperty("url"), pgDsProps);
	}*/

	/*@Bean
	public PlatformTransactionManager txManager() {
		return new DataSourceTransactionManager(dataSource);
	}*/

	@Bean
	public JdbcTemplate getJdbcTemplate() {
		return new JdbcTemplate(dataSource);
	}

	@Test
	public void run() {
		logger.info("RUNNING>>>>>>........");
		logger.info("Datasource = " + this.dataSource);
		listAllUsers();
		addAUser();
		listAllUsers();
		addSomeUsers();
		listAllUsers();
		updateAUser();
		listAllUsers();
		
		createSomeTasks();
		findAllTasks();
		
		closeSomeTasks();
		findAllTasks();
		
		findCompletedTasks();
		findOpenTasks();
		
		findTasksByAssignee();
	}

	void listAllUsers() {
		logger.debug("======================================");
		logger.debug("==== Listing all Users .....");
		List<User> allUsers = userService.findAllUsers();
		logger.debug("==== allUsers = " + allUsers + " ====");
		logger.debug("==== There are " + allUsers.size() + " users currently in the system ====");
		logger.debug("======================================");
	}

	void addAUser() {
		userService.createNewUser(new User(null, "Rineesh K", "rineesh", "password987", prepareDate(1, 1, 1978)));
	}

	void addSomeUsers() {
		userService.createNewUser(new User(null, "Tarun Bhati", "tbhati", "password456", prepareDate(1, 1, 1975)));
		userService.createNewUser(new User(null, "Nuruthin Ahmed", "nahmed", "password567", prepareDate(1, 6, 1977)));
		userService
				.createNewUser(new User(null, "Nitin Kulkarni", "nkulkarni", "password678", prepareDate(1, 8, 1974)));
	}

	void updateAUser() {
		User tBhati = userService.findByUserName("tbhati");
		tBhati.setName("Tarun Middle-name Bhati");
		tBhati.setDateOfBirth(prepareDate(10, 12, 1974));
		userService.updateUser(tBhati);
		User newTBhati = userService.findByUserName("tbhati");
		assert tBhati.getName().equals(newTBhati.getName());
	}
	
	void createSomeTasks() {
		User shameer = userService.findByUserName("sameerean");
		User hamid = userService.findByUserName("hamid");
		
		Task task1 = new Task("Order Breakfast", 1, "Open", shameer, new Date(), hamid, "test comments...");
		taskService.createTask(task1);
		logger.info("Task Created.." + task1);

		Task task2 = new Task("Order Lunch", 1, "Open", shameer, new Date(), hamid, "test comments...2");
		taskService.createTask(task2);
		logger.info("Task Created.." + task2);

		Task task3 = new Task("Order Snacks", 1, "Open", hamid, new Date(), shameer, "test comments...3");
		taskService.createTask(task3);
		logger.info("Task Created.." + task3);

		Task task4 = new Task("Order Dinner", 2, "Open", hamid, new Date(), shameer, "test comments...4");
		taskService.createTask(task4);
		logger.info("Task Created.." + task4);
	}
	
	void closeSomeTasks() {
		User shameer = userService.findByUserName("sameerean");
		List<Task> shameerTasks = taskService.findTasksByAssignee(shameer.getUserName());
		
		for (Task task : shameerTasks) {
			taskService.completeTask(task.getId(), "Hey I am closing it", shameer.getId());
			logger.info("Task Completed.." + task);
		}

	}
	
	void findAllTasks() {
		logger.info("============ Finding all tasks... ================ ");
		logger.info("== There are " + taskService.findAllTasksCount() + " tasks in the system");
		List<Task> allTasks = taskService.findAllTasks();
		logger.info("allTasks = " + allTasks);
	}
	
	void findOpenTasks() {
		logger.info("============ Finding all open tasks... ================ ");
		logger.info("== There are " + taskService.findAllOpenTasksCount() + " Open tasks in the system");
		List<Task> allTasks = taskService.findAllOpenTasks();
		logger.info("openTasks = " + allTasks);
	}
	
	void findCompletedTasks() {
		logger.info("============ Finding all Completed tasks... ================ ");
		logger.info("== There are " + taskService.findAllCompletedTasksCount() + " completed tasks in the system");
		List<Task> allTasks = taskService.findAllCompletedTasks();
		logger.info("completedTasks = " + allTasks);
	}
	
	void findTasksByAssignee() {
		logger.info("============ Finding all Tasks by shameer... ================ ");
		List<Task> tasks = taskService.findTasksByAssignee("sameerean");
		logger.info("Tasks by shameer = " + tasks);
		
		logger.info("============ Finding Completed Tasks by shameer... ================ ");
		tasks = taskService.findCompletedTasksByAssignee("sameerean");
		logger.info("Completed Tasks by shameer = " + tasks);
		
		logger.info("============ Finding Open Tasks by shameer... ================ ");
		tasks = taskService.findOpenTasksByAssignee("sameerean");
		logger.info("Open Tasks by shameer = " + tasks);
		
	}

	private Date prepareDate(int dd, int mm, int yyyy) {
		return new GregorianCalendar(yyyy, mm, dd).getTime();
	}
	
	
	@After
	public void tearDown() {
		logger.info("Shutting down the Database......");
//		this.dataSource.shutdown();
	}
	

}
