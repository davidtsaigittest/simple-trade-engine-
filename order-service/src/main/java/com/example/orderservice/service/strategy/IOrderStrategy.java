package com.example.orderservice.service.strategy;

import com.example.orderservice.bean.Order;
import com.example.orderservice.bean.OrderSide;
import com.example.orderservice.bean.Trade;

import java.util.List;

public interface IOrderStrategy {

    List<Trade> buyOrder(Order order);

    List<Trade> sellOrder(Order order);

    default List<Trade> process(Order order) {
        if (order.getSide() == OrderSide.BUY) {
            return buyOrder(order);
        } else {
            return sellOrder(order);
        }
    }

}
