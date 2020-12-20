package com.springessentialsbook.chapter1.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.springessentialsbook.chapter1.service.BannerService;
import com.springessentialsbook.chapter1.service.GreetingService;
import com.springessentialsbook.chapter1.settings.SystemSettings;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "com.springessentialsbook")
public class SpringJavaConfigurator {
	
	@Autowired
	private GreetingService greeter;
	
	@Autowired
	private BannerService banner;
	
	@Autowired
	@Lazy
	private SystemSettings systemSettings;
	
	@Autowired
	private Environment env;
	
	@Bean
	public BannerService createBanner() {
		return new BannerService();
	}
	
	public BannerService getBanner() {
		return this.banner;
	}
	
	@Bean
	public SystemSettings getSystemSettings() {
		int openUserTasksMaxLimit = Integer.parseInt(env.getProperty("open.user.task.maxlimit"));
		String dateFormat = env.getProperty("system.date-format");
		String appDisplayName = env.getProperty("app.displayname");
		String adminEmail = env.getProperty("admin.email");
		return new SystemSettings(openUserTasksMaxLimit, dateFormat, appDisplayName, adminEmail);
	}
	
	public void run() {
		this.banner.displayBanner();
		this.greeter.greet("I am the Greeter Spring bean, configured with Java Configuration.");
		
		this.systemSettings = getSystemSettings();
		this.greeter.greet("System Settings = " + this.systemSettings);
	}

}
