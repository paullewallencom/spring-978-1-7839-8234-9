package com.springessentialsbook.chapter1.boot;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.springessentialsbook.chapter1.service.BannerService;
import com.springessentialsbook.chapter1.service.GreetingService;
import com.springessentialsbook.chapter1.service.TaskService;
import com.springessentialsbook.chapter1.service.UserService;

@Configuration
@ComponentScan(basePackages = "com.springessentialsbook")
@EnableAspectJAutoProxy
public class AOPJavaConfigurator {
	
	@Autowired
	private GreetingService greeter;
	
	@Autowired
	private BannerService banner;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private UserService userService;
	
	@Bean
	public BannerService createBanner() {
		return new BannerService();
	}
	
	public BannerService getBanner() {
		return this.banner;
	}
	
	public TaskService getTaskService() {
		return this.taskService;
	}
	
	public UserService getUserService() {
		return this.userService;
	}
	
	public void run() {
		this.banner.displayBanner();
		this.greeter.greet("I am the Greeter Spring bean, configured with Java Configuration.");
	}
	
	@PostConstruct
	public void loadDefaultUsers() {
		this.userService.createNewUser("Shameer Kunjumohamed", "shameer", "n0thinG");
		this.userService.createNewUser("Hamidreza Sattari", "hamid", "1rrelev@nt");
	}

}
