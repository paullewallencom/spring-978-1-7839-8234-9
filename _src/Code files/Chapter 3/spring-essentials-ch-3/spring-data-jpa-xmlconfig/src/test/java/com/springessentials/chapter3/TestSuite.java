package com.springessentials.chapter3;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.springessentials.chapter3.domain.Task;
import com.springessentials.chapter3.domain.User;
import com.springessentials.chapter3.service.TaskService;
import com.springessentials.chapter3.service.UserService;

//@ContextConfiguration(classes = JpaConfiguration.class)
@ContextConfiguration(locations={"classpath:/META-INF/spring/app-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestSuite {

	private static final Logger logger = LoggerFactory.getLogger(TestSuite.class);

	@Autowired
	private UserService userService;

	@Autowired
	private TaskService taskService;

	@Test
	public void saveUser() {
		logger.info("Inside saveUser....userDAO = " + userService);
		User user = new User(null, "Shameer PK", "sameerean", "password123", new Date());
		User createdUser = userService.createNewUser(user);
		logger.info("user = " + createdUser);
	}

	@Test
	public void run() {
		logger.info("RUNNING>>>>>>........");
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

		List<User> userList = new ArrayList<>();
		userList.add(new User(null, "Shameer Kunjumohamed", "sameereab", "password98456", prepareDate(8, 2, 1977)));
		userList.add(new User(null, "Hamidreza Sattari", "hamid", "password8978967", prepareDate(1, 1, 1975)));
		userList.add(new User(null, "Tarun Bhati", "tbhati", "password456", prepareDate(1, 1, 1975)));
		userList.add(new User(null, "Nuruthin Ahmed", "nahmed", "password567", prepareDate(1, 6, 1977)));
		userList.add(new User(null, "Nitin Kulkarni", "nkulkarni", "password678", prepareDate(1, 8, 1974)));

		userService.createUsers(userList);
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

}
