package com.springessentialsbook.chapter1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.springessentialsbook.chapter1.service.GreetingService;

public class Application {

	public static void main(String[] args) {
		ApplicationContext context =
			    new ClassPathXmlApplicationContext(new String[] {"application-context.xml"});
		GreetingService greeter = (GreetingService) context.getBean("Greeter");
		greeter.greet("I am your first Spring bean instance, configured purely with XML metadata. I am resolved by name.");
		
		GreetingService greeter2 = (GreetingService) context.getBean(GreetingService.class);
		greeter2.greet("I am your first Spring bean instance, configured purely with XML metadata. I am resolved by type.");
		
		System.out.println("The two instances are" + ((greeter == greeter2) ? " same as it's a singleton!" : " not same as it's a prototype!"));
	}
}
