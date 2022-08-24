package com.example.orderservice.service;

import com.example.orderservice.bean.LimitOrder;
import com.example.orderservice.bean.Side;

import java.util.List;
import java.util.Map;

public interface IOrderSideService {
   Map<String, Object> addOrder(Side side, double quantity, double price);

}
