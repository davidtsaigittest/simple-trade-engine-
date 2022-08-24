package com.example.orderservice.service;

import com.example.orderservice.bean.LimitOrder;
import com.example.orderservice.bean.Order;
import com.example.orderservice.module.OrderBook;
import com.example.orderservice.bean.Side;
import com.example.orderservice.bean.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class OrderService implements IOrderService {

    private OrderBook orderBook;

    @Autowired
    public void setOrderBook(OrderBook orderBook) {
        this.orderBook = orderBook;
    }

    @Override
    public Map<String, Order> getOrder(String id, Side side) {
        Map<String, Order> response = new HashMap<>();

        response.put("order", this.orderBook.findOrderById(id, side));

        return response;
    }

    @Override
    public Map<String, List<Order>> getOrders() {
        Map<String, List<Order>> response = new HashMap<>();
        response.put(Side.BUY.toString(), this.orderBook.getBuyOrders());
        response.put(Side.SELL.toString(), this.orderBook.getSellOrders());
        return response;
    }

    @Override
    public Map<String, Object> addOrder(Side side, double quantity, double price) {
        Map<String, Object> response = new HashMap<>();

        Order order = new LimitOrder.Builder(side).withQuantity(quantity)
                .atPrice(price).build();

        List<Trade> trades = this.orderBook.process(order);

        response.put("orderId", order.getId());
        response.put("result", trades);
        
        return response;
    }


}
