package com.springessentialsbook.chapter1.boot;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.springessentialsbook.chapter1.domain.Task;
import com.springessentialsbook.chapter1.domain.User;
import com.springessentialsbook.chapter1.service.GreetingService;
import com.springessentialsbook.chapter1.service.TaskService;
import com.springessentialsbook.chapter1.service.UserService;
import com.springessentialsbook.chapter1.settings.SystemSettings;

public class ApplicationXMLContext {
	public static final Logger logger = LoggerFactory.getLogger(ApplicationXMLContext.class);

	public static void main(String[] args) {
		ApplicationContext context =
			    new ClassPathXmlApplicationContext(new String[] {"xml-application-context.xml"});
		GreetingService greeter = (GreetingService) context.getBean("SimpleGreeter");
		greeter.greet("I am your first Spring bean instance, configured purely with XML metadata. I am resolved by name.");
		
		GreetingService greeter2 = (GreetingService) context.getBean(GreetingService.class);
		greeter2.greet("I am your first Spring bean instance, configured purely with XML metadata. I am resolved by type.");
		
		logger.debug("The two instances are" + ((greeter == greeter2) ? " same as it's a singleton!" : " not same as it's a prototype!"));
		SystemSettings settings = (SystemSettings) context.getBean("systemSettings");
		
		logger.debug("settings = " + settings);
		
		TaskService taskService = context.getBean(TaskService.class);
		
		UserService userService = context.getBean(UserService.class);
		
		User shameer = userService.findByUserName("shameer");
		User hamid = userService.findByUserName("hamid");
		
		taskService.createTask("Book tickets", 3, shameer.getId(), hamid.getId());
		
		List<Task> allOpenTasks = taskService.findAllOpenTasks();
		
		logger.info("allOpenTasks = " + allOpenTasks);

	}

}
