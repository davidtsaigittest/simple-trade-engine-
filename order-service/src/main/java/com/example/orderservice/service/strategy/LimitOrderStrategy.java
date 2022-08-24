package com.example.orderservice.service.strategy;

import com.example.orderservice.bean.Order;
import com.example.orderservice.bean.Trade;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class LimitOrderStrategy implements IOrderStrategy {

    private Order order;
    private LinkedList<Order> buyOrders;

    private LinkedList<Order> sellOrders;

    public LimitOrderStrategy() {
    }

    public LimitOrderStrategy(Order order, LinkedList<Order> buyOrders, LinkedList<Order> sellOrders) {
        this.order = order;
        this.buyOrders = buyOrders;
        this.sellOrders = sellOrders;
    }

    @Override
    public List<Trade> buyOrder(Order order) {
        return null;
    }

    @Override
    public List<Trade> sellOrder(Order order) {
        return null;
    }
}
