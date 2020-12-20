package com.springessentialsbook.chapter1.boot;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;

import com.springessentialsbook.chapter1.service.BannerBean;

public class ApplicationJavaConfig {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringJavaConfigurator.class);

		SpringJavaConfigurator app = ctx.getBean(SpringJavaConfigurator.class);
		app.run();

		BannerBean banner2 = ctx.getBean(BannerBean.class);

		banner2.displayBanner();

		app.getBanner().displayBanner();
		
		ctx.getEnvironment().setActiveProfiles("dev");
		
		Resource classPathResource = ctx.getResource("classpath:scripts/tasks-schema.sql");

		Resource fileResource = ctx.getResource("file:///scripts/master-data.sql");
		
		Resource urlResource = ctx.getResource("http://country.io/names.json");
	}

}
