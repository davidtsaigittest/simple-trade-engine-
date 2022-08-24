package com.example.orderservice.service.strategy;

import com.example.orderservice.bean.Order;
import com.example.orderservice.bean.Trade;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MarketOrderStrategy implements IOrderStrategy {

    private LinkedList<Order> buyOrders;

    private LinkedList<Order> sellOrders;

    private Order order;

    public MarketOrderStrategy() {
    }

    public MarketOrderStrategy(Order order, LinkedList<Order> buyOrders, LinkedList<Order> sellOrders) {
        this.order = order;
        this.buyOrders = buyOrders;
        this.sellOrders = sellOrders;
    }

    @Override
    public List<Trade> buyOrder(Order order) {

        ArrayList<Trade> buyTrades = new ArrayList<>();

        double buyOrderQuantity = order.getQuantity();

        // 檢查SellOrderQueue是否為空
        if (!this.sellOrders.isEmpty())
        {
            for (int i = 0; i < sellOrders.size(); i++)
            {
                Order sellOrder = sellOrders.get(i);

                if (sellOrder.getPrice() == order.getPrice()) {

                    double sellOrderQuantity = sellOrder.getQuantity();

                    if (sellOrderQuantity >= buyOrderQuantity) {

                        buyTrades.add(new Trade(order.getId(), sellOrder.getId(),
                                order.getQuantity(), sellOrder.getPrice()));

                        sellOrder.setQuantity(sellOrderQuantity - buyOrderQuantity);

                    } else {

                        buyOrderQuantity -= sellOrderQuantity;

                        if (sellOrderQuantity == 0) {
                            this.sellOrders.remove(i);
                        }
                        sellOrder.setQuantity(0);

                        buyTrades.add(new Trade(order.getId(), sellOrder.getId(),
                                sellOrderQuantity, sellOrder.getPrice()));
                    }
                }
            }
        }
        // enqueue buy orders
        if (buyOrderQuantity != 0) {
            order.setQuantity(buyOrderQuantity);
            this.buyOrders.add(order);
        }

        return buyTrades;
    }

    @Override
    public List<Trade> sellOrder(Order order) {
        ArrayList<Trade> sellTrades = new ArrayList<>();

        double sellOrderQuantity = order.getQuantity();

        if (!this.buyOrders.isEmpty()) {

            for (int i = 0; i < this.buyOrders.size(); i++) {

                Order buyOrder = buyOrders.get(i);

                double buyQuantity = buyOrder.getQuantity();

                if (buyOrder.getPrice() == order.getPrice()) {

                    if (buyQuantity >= sellOrderQuantity) {

                        sellTrades.add(new Trade(buyOrder.getId(), order.getId(),
                                sellOrderQuantity, order.getPrice()));

                        buyQuantity -= sellOrderQuantity;

                        if (buyQuantity == 0) {
                            this.buyOrders.remove(i);
                        }

                    } else {
                        sellOrderQuantity -= buyQuantity;
                        sellTrades.add(new Trade(buyOrder.getId(), order.getId(),
                                buyQuantity, order.getPrice()));
                    }
                }
            }
        }

        // enqueue sell orders
        if (sellOrderQuantity != 0) {
            // update quantity
            order.setQuantity(sellOrderQuantity);
            this.sellOrders.add(order);
        }

        return sellTrades;
    }
}
