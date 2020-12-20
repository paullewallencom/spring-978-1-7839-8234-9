package com.springessentialsbook.chapter7.struts;

import com.springessentialsbook.chapter7.service.OrderService;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import com.opensymphony.xwork2.ActionSupport;
@Action("/doOrder")
@ResultPath("/WEB-INF/pages")
@Results({
	@Result(name = "success", location = "orderProceed.jsp"),
	@Result(name = "error", location = "failedOrder.jsp")
})
public class DoOrderAction extends ActionSupport {
	@Autowired
	private OrderService orderService;
	private OrderVO order;

	public void setOrder(OrderVO order) {
		this.order = order;
	}
	
	public OrderVO getOrder() {
		return order;
	}

	@Override
	public String execute()throws Exception {
		if ( orderService.isValidOrder(order.getOrderId()))  {
			order.setOrderName(orderService.placeAnOrder(order.getOrderId()));
			return SUCCESS;
		}
		return ERROR;
	}
}
