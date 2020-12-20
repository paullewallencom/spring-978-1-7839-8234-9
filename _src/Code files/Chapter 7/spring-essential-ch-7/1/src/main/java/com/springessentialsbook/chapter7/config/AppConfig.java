package com.springessentialsbook.chapter7.config;

import com.springessentialsbook.chapter7.service.OrderService;
import com.springessentialsbook.chapter7.service.serviceImpl.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.springessentialsbook.chapter7")
public class AppConfig {
   @Bean
   public OrderService orderService(){
	   return new OrderServiceImpl();
   }


}
