package com.example.orderservice.service;

import com.example.orderservice.bean.Order;
import com.example.orderservice.bean.OrderSide;

import java.util.List;
import java.util.Map;

public interface IOrderGetService {

    Map<String, Order> getOrder(String id, OrderSide side);

    Map<String, List<Order>> getOrders();
}
