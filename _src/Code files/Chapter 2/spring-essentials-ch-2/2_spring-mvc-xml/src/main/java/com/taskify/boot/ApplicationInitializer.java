package com.taskify.boot;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class ApplicationInitializer implements WebApplicationInitializer {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationInitializer.class);

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		logger.info("===== Application is starting up! ========");
		/*
		XmlWebApplicationContext appContext = new XmlWebApplicationContext();
		appContext.setConfigLocation("/WEB-INF/spring/appServlet/servlet-context.xml");

		ServletRegistration.Dynamic registration = servletContext.addServlet("rootDispatcher",
				new DispatcherServlet(appContext));
		registration.setLoadOnStartup(1);
		registration.addMapping("/");
		registration.setAsyncSupported(true);
*/
	}

}
