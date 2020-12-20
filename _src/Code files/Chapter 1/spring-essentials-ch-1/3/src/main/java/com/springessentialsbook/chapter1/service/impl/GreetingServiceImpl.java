package com.springessentialsbook.chapter1.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.springessentialsbook.chapter1.service.GreetingService;

@Component("Greeter")
public class GreetingServiceImpl implements GreetingService {

	Logger logger = LoggerFactory.getLogger(GreetingServiceImpl.class);

	public void greet(String message) {
		logger.info("Greetings! " + message);
	}

	public static final GreetingService newInstance() {
		return new GreetingServiceImpl();
	}

}
