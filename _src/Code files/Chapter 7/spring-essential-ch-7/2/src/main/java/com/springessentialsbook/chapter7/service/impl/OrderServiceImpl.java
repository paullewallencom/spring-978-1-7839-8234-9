package com.springessentialsbook.chapter7.service.impl;

import com.springessentialsbook.chapter7.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {


	public String placeAnOrder(String id) {
		System.out.println("Following orderid is placed:"+ id);
		return "Product-" + id;

	}

	public boolean isValidOrder(String orderId) {
		 return orderId!=null && orderId.length()>0;
	}
}
