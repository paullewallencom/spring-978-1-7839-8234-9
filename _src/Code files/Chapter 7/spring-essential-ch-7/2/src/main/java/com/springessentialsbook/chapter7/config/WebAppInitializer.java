/** 
 * Copyright (C) CodeJava.net from 2012 To Present
 * All rights reserved. 
 */
package com.springessentialsbook.chapter7.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class WebAppInitializer implements WebApplicationInitializer {


	public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext appContext
        	= new AnnotationConfigWebApplicationContext();
        appContext.register(ApplicationContextConfig.class);
        
        ContextLoaderListener contextLoaderListener = new ContextLoaderListener(appContext);
        servletContext.addListener(contextLoaderListener);
        
        FilterRegistration.Dynamic filter = servletContext.addFilter(
        		"StrutsDispatcher", new StrutsPrepareAndExecuteFilter());
        filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");

	}

}
