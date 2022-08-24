package com.example.orderservice.service;

import com.example.orderservice.bean.LimitOrder;
import com.example.orderservice.bean.Order;
import com.example.orderservice.bean.Side;

import java.util.List;
import java.util.Map;

public interface IOrderGetService {

    Map<String, Order> getOrder(String id, Side side);

    Map<String, List<Order>> getOrders();
}
