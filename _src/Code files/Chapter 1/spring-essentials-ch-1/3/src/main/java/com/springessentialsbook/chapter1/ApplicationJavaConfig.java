package com.springessentialsbook.chapter1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationJavaConfig {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				SpringJavaConfigurator.class);
		
		SpringJavaConfigurator app = ctx.getBean(SpringJavaConfigurator.class);
		app.run();
		
		BannerBean banner2 = ctx.getBean(BannerBean.class);
		
		banner2.displayBanner();
		
		app.getBanner().displayBanner();
		
	}

}
