package com.springessentialsbook.chapter1.boot;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.springessentialsbook.chapter1.service.GreetingService;

public class ApplicationSimpleComponentScan {

	public static void main(String[] args) {
		ApplicationContext context =
			    new ClassPathXmlApplicationContext(new String[] {"application-context.xml"});
		GreetingService app = (GreetingService) context.getBean(GreetingService.class);
		app.greet("I am the Greeter Spring bean, configured with annotation.");
	}

}
