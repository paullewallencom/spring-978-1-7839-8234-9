package com.springessentials.chapter3.test;

import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.springessentials.chapter3.domain.Task;
import com.springessentials.chapter3.domain.User;
import com.springessentials.chapter3.service.TaskService;
import com.springessentials.chapter3.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class HsqldbSpringJdbcTest {
	private static final Logger logger = LoggerFactory.getLogger(HsqldbSpringJdbcTest.class);

	@Autowired
	private EmbeddedDatabase dataSource;

	@Autowired
	private UserService userService;

	@Autowired
	private TaskService taskService;

	@Configuration
	@ComponentScan(basePackages = { "com.springessentials.chapter3.service.impl", "com.springessentials.chapter3.dao.impl" }, excludeFilters = {
			@Filter(type = FilterType.ASSIGNABLE_TYPE, value = com.springessentials.chapter3.dao.impl.UserSimpleJdbcInsertDAO.class),
			@Filter(type = FilterType.ASSIGNABLE_TYPE, value = com.springessentials.chapter3.dao.impl.PostgresTaskJdbcDAO.class),
			@Filter(type = FilterType.ASSIGNABLE_TYPE, value = com.springessentials.chapter3.dao.impl.UserNamedParameterJdbcDAO.class) })
	@EnableTransactionManagement
	static class Config {

		@Autowired
		@Qualifier("HsqlDatasource")
		private EmbeddedDatabase dataSource;

		@Bean(name = "HsqlDatasource")
		@Primary
		EmbeddedDatabase getHsqlDatasource() {

			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
					.generateUniqueName(true)
					.setName("SPESS")
					.addScript("db-scripts/hsql/db-schema.sql")
					.addScript("db-scripts/hsql/data.sql")
					.addScript("db-scripts/hsql/storedprocs.sql")
					.addScript("db-scripts/hsql/functions.sql")
					.setSeparator("/")
					.build();
		}

		@Bean
		public PlatformTransactionManager txManager() {
			return new DataSourceTransactionManager(dataSource);
		}

		@Bean
		public JdbcTemplate getJdbcTemplate() {
			return new JdbcTemplate(dataSource);
		}
	}

	@Test
	@Transactional
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
		userService.createNewUser(new User(null, "Nitin Kulkarni", "nkulkarni", "password678", prepareDate(1, 8, 1974)));
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
		// logger.info("== There are " + taskService.findAllTasksCount() +
		// " tasks in the system");
		List<Task> allTasks = taskService.findAllTasks();
		logger.info("allTasks = " + allTasks);
	}
	
	@Test(expected = BadSqlGrammarException.class)
	public void testExceptionResolver() {
		
		String userQuery = "select * from TBL_NONE where name =  ?";
		
		SQLExceptionTranslator excTranslator = new SQLExceptionTranslator() {
			
			@Override
			public DataAccessException translate(String task, String sql, SQLException ex) {
				logger.info("SUCCESS --- SQLExceptionTranslator.translate invoked !!");
				return new BadSqlGrammarException("Invalid Query", userQuery, ex){};
			}
		};
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.setExceptionTranslator(excTranslator);
		
		Map<String, Object> result = jdbcTemplate.queryForMap(userQuery, new Object[] {"abc"});
		
		
		logger.info("Result = " + result);

	}

	private Date prepareDate(int dd, int mm, int yyyy) {
		return new GregorianCalendar(yyyy, mm, dd).getTime();
	}

	@After
	public void tearDown() {
		logger.info("Shutting down the Database......");
		this.dataSource.shutdown();
	}

	@Test
	public void test() {
		// fail("Not yet implemented");
		logger.info("Test......");
	}

}
