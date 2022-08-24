package com.example.orderservice.module;


import com.example.orderservice.bean.Order;
import com.example.orderservice.bean.OrderType;
import com.example.orderservice.bean.OrderSide;
import com.example.orderservice.bean.Trade;
import com.example.orderservice.service.strategy.IOrderStrategy;
import com.example.orderservice.service.strategy.LimitOrderStrategy;
import com.example.orderservice.service.strategy.MarketOrderStrategy;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
  設計要點:
  1. 目前每個商品都會有一個OrderBook，目前只有一個OrderBook(context)
  2. 不同OrderType有不同的process Strategy(可使用Strategy Pattern處理)
  3. 需要考慮multi-thread情況下存取屬性時concurrency問題
 */
@Component
public class OrderBook implements IOrderBook {

    private IOrderStrategy processStrategy;

    private LinkedList<Order> buyOrders;

    private LinkedList<Order> sellOrders;


    public OrderBook() {
        this.buyOrders = new LinkedList<>();
        this.sellOrders = new LinkedList<>();
    }

    @Override
    public synchronized List<Trade> process(Order order) {

        if (order.getType() == OrderType.MARKET) {
            processStrategy = new MarketOrderStrategy(order, buyOrders, sellOrders);
        } else {
            processStrategy = new LimitOrderStrategy(order, buyOrders, sellOrders);
        }
        return processStrategy.process(order);
    }

    public synchronized Order findOrderById(final String id, final OrderSide side) {
        List<Order> search;
        if (side == OrderSide.BUY) {
            search = this.buyOrders;
        } else {
            search = this.sellOrders;
        }
        return search.stream().filter(order -> order.getId().equals(id))
                .findFirst().orElse(null);
    }


    /**
     * @return the buyOrders
     */
    public synchronized List<Order> getBuyOrders() {
        return this.buyOrders;
    }

    public synchronized List<Order> getSellOrders() {
        return this.sellOrders;
    }
}
