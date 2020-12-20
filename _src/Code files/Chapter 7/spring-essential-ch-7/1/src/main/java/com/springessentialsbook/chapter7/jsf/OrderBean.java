package com.springessentialsbook.chapter7.jsf;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.springessentialsbook.chapter7.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@ManagedBean(name = "orderBean", eager = true)
@RequestScoped
@Component
public class OrderBean {
	private String orderName;
	private Integer orderId;
	
	@Autowired
	public OrderService orderService;
	public String placeAnOrder(){
		orderName=orderService.placeAnOrder(orderId);
		return "confirmation";
	}
	
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}


	
}