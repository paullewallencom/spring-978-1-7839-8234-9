package com.springessentialsbook.chapter7.service.serviceImpl;

import com.springessentialsbook.chapter7.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {


	public String placeAnOrder(Integer id) {
		return "Product-" + id;

	}
}
