package com.example.orderservice.module;


import com.example.orderservice.bean.LimitOrder;
import com.example.orderservice.bean.Order;
import com.example.orderservice.bean.Side;
import com.example.orderservice.bean.Trade;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
 *  只有一個OrderBook
 *  需要考慮multi-thread存取問題
 */
@Component
public class OrderBook implements IOrderBook {

    private LinkedList<Order> buyOrders;

    private LinkedList<Order> sellOrders;


    public OrderBook() {
        this.buyOrders = new LinkedList<>();
        this.sellOrders = new LinkedList<>();
    }

    @Override
    public synchronized List<Trade> process(Order order) {
        if (order.getSide() == Side.BUY) {
            return this.doBuyOrder(order);
        } else {
            return this.doSellOrder(order);
        }
    }

    private synchronized List<Trade> doBuyOrder(Order order) {

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

    private synchronized List<Trade> doSellOrder(Order order) {

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

    public synchronized Order findOrderById(final String id, final Side side) {
        List<Order> search;
        if (side == Side.BUY) {
            search = this.buyOrders;
        } else {
            search = this.sellOrders;
        }
        return search.stream().filter(order -> order.getId().equals(id))
                .findFirst().orElse(null);
    }

    private synchronized void removeBuyOrder(int index) {
        this.buyOrders.remove(index);
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
