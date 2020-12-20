package com.springessentialsbook.chapter1.boot;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.springessentialsbook.chapter1.domain.Task;
import com.springessentialsbook.chapter1.domain.User;
import com.springessentialsbook.chapter1.service.BannerService;
import com.springessentialsbook.chapter1.service.TaskService;
import com.springessentialsbook.chapter1.service.UserService;

public class ApplicationAOPJavaConfig {
	
	public static final Logger logger = LoggerFactory.getLogger(ApplicationAOPJavaConfig.class);

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AOPJavaConfigurator.class);

		AOPJavaConfigurator app = ctx.getBean(AOPJavaConfigurator.class);
//		app.run();

		BannerService banner2 = ctx.getBean(BannerService.class);

		banner2.displayBanner();

		app.getBanner().displayBanner();
		
		TaskService taskService = app.getTaskService();
		
		UserService userService = app.getUserService();
		
		User shameer = userService.findByUserName("shameer");
		User hamid = userService.findByUserName("hamid");
		
		taskService.createTask("Book tickets", 3, shameer.getId(), hamid.getId());
		
		List<Task> allOpenTasks = taskService.findAllOpenTasks();
		
		logger.info("allOpenTasks = " + allOpenTasks);

	}

}
