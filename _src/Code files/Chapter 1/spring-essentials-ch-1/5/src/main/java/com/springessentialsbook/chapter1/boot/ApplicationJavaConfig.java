package com.springessentialsbook.chapter1.boot;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.springessentialsbook.chapter1.service.BannerService;

public class ApplicationJavaConfig {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringJavaConfigurator.class);

		SpringJavaConfigurator app = ctx.getBean(SpringJavaConfigurator.class);
		app.run();

		BannerService banner2 = ctx.getBean(BannerService.class);

		banner2.displayBanner();

		app.getBanner().displayBanner();

	}

}
