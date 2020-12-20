package com.springessentialsbook.chapter1.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.springessentialsbook.chapter1.service.BannerBean;
import com.springessentialsbook.chapter1.service.GreetingService;

@Configuration
@ComponentScan(basePackages = "com.springessentialsbook")
public class TasksSystemConfigurator {
	
	@Autowired
	private GreetingService greeter;
	
	@Autowired
	@Lazy
	private BannerBean banner;
	
	@Bean
	public BannerBean createBanner() {
		return new BannerBean();
	}
	
//	public 
	
	public BannerBean getBanner() {
		return this.banner;
	}
	
	public void run() {
		this.banner.displayBanner();
		this.greeter.greet("I am the Greeter Spring bean, configured with Java Configuration.");
	}

}
