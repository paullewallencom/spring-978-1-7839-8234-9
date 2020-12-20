package com.taskify.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationInitializer.class);
	
	
	@Override
    protected Class<?>[] getRootConfigClasses() {
		logger.info("===== Application is starting up! ========getRootConfigClasses()");
        return new Class[] { ApplicationConfiguration.class };
    }
  
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }
  
    @Override
    protected String[] getServletMappings() {
//        return new String[] { "/api/v1/*" };
        return new String[] { "/" };
    }

	
/*
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		logger.info("===== Application is starting up! ========");

		XmlWebApplicationContext appContext = new XmlWebApplicationContext();
		appContext.setConfigLocation("/WEB-INF/spring/appServlet/servlet-context.xml");

		ServletRegistration.Dynamic servlet = servletContext.addServlet("appServlet",
				new DispatcherServlet(appContext));
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
		servlet.setAsyncSupported(true);

		servlet.setMultipartConfig(new MultipartConfigElement("/tmp/servlet-uploads", 20848820, 418018841, 1048576));

		FilterRegistration.Dynamic filter = servletContext.addFilter("httpMethodFilter",
				"org.springframework.web.filter.HiddenHttpMethodFilter");
		filter.addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST), true, "appServlet");
		filter.setAsyncSupported(true);

		servletContext.addListener(org.springframework.web.context.ContextLoaderListener.class);

	}
*/
}
