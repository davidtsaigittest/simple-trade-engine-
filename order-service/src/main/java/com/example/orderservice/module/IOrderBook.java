package com.example.orderservice.module;

import com.example.orderservice.bean.Order;
import com.example.orderservice.bean.Trade;

import java.util.List;

public interface IOrderBook {

     List<Trade> process(Order order);

}
