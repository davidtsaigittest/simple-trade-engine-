package com.example.orderservice.service;

import com.example.orderservice.bean.OrderType;
import com.example.orderservice.bean.OrderSide;

import java.util.Map;

public interface IOrderSideService {
   Map<String, Object> addOrder(OrderSide side, double quantity, double price, OrderType type);

}
