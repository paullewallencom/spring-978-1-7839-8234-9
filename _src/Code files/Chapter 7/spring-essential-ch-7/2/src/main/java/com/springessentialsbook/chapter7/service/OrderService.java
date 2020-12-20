package com.springessentialsbook.chapter7.service;


public interface OrderService {
   String placeAnOrder(String orderId);
   boolean isValidOrder(String orderId);
}
