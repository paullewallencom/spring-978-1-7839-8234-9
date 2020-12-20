package com.springessentials.chapter3.boot;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.springessentials.chapter3.domain.Task;
import com.springessentials.chapter3.domain.User;
import com.springessentials.chapter3.service.TaskService;
import com.springessentials.chapter3.service.UserService;

//@Configuration
//@ComponentScan(basePackages = "com.springessentials.chapter3")
//@EnableTransactionManagement
public class ApplicationJavaConfig {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationJavaConfig.class);

	@Autowired
	private DataSource dataSource;

	@Autowired
	private UserService userService;

	@Autowired
	private TaskService taskService;

	@Bean
	DataSource getHsqlDatasource() {

		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
				.addScript("db-scripts/hsql/db-schema.sql")
				.addScript("db-scripts/hsql/data.sql")
				.addScript("db-scripts/hsql/storedprocs.sql")
				.addScript("db-scripts/hsql/functions.sql")
				.setSeparator("/")
				.build();
		//
		// DriverManagerDataSource dataSource = new
		// DriverManagerDataSource().setDriverClassName("org.hsqldb.jdbcDriver");
		// dataSource.setUrl("jdbc:hsqldb:hsql://localhost:");
		// dataSource.setUsername("sa");
		// dataSource.setPassword("");
		// return dataSource;
	}

	@Bean
	public PlatformTransactionManager txManager() {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public JdbcTemplate getJdbcTemplate() {
		return new JdbcTemplate(dataSource);
	}

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
		Task task1 = new Task("Order Breakfast", 1, "Open", userService.findById(1l), new Date(), userService.findById(2l), "test comments...");
		taskService.createTask(task1);
		logger.info("Task Created.." + task1);

		Task task2 = new Task("Order Lunch", 1, "Open", userService.findById(1l), new Date(), userService.findById(2l), "test comments...2");
		taskService.createTask(task2);
		logger.info("Task Created.." + task2);

		Task task3 = new Task("Order Snacks", 1, "Open", userService.findById(2l), new Date(), userService.findById(1l), "test comments...3");
		taskService.createTask(task3);
		logger.info("Task Created.." + task3);

		Task task4 = new Task("Order Dinner", 2, "Open", userService.findById(2l), new Date(), userService.findById(3l), "test comments...4");
		taskService.createTask(task4);
		logger.info("Task Created.." + task4);
	}
	
	void findAllTasks() {
		logger.info("============ Finding all tasks... ================ ");
//		logger.info("== There are " + taskService.findAllTasksCount() + " tasks in the system");
		List<Task> allTasks = taskService.findAllTasks();
		logger.info("allTasks = " + allTasks);
	}

	private Date prepareDate(int dd, int mm, int yyyy) {
		return new GregorianCalendar(yyyy, mm, dd).getTime();
	}
	
	@PostConstruct
	public void startDBManager() {
			
		//hsqldb
//		DatabaseManagerSwing.main(new String[] { "--url", "jdbc:hsqldb:mem:testdb", "--user", "sa", "--password", "" });

		//derby
		//DatabaseManagerSwing.main(new String[] { "--url", "jdbc:derby:memory:testdb", "--user", "", "--password", "" });

		//h2
		//DatabaseManagerSwing.main(new String[] { "--url", "jdbc:h2:mem:testdb", "--user", "sa", "--password", "" });

	}

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationJavaConfig.class);

		ApplicationJavaConfig app = ctx.getBean(ApplicationJavaConfig.class);
		app.run();

		ctx.close();

	}

}
