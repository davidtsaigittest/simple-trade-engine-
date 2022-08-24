package com.example.orderservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.example.orderservice.AbstractTest;
import com.example.orderservice.bean.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.example.orderservice.module.OrderBook;


public class OrderServiceTests extends AbstractTest {

    /**
     * Mock order book.
     */
    @Mock
    private OrderBook orderBook;

    /**
     * Inject OrderBook to OrderService's field "orderBook"
     */
    @InjectMocks
    @Resource
    private OrderService orderService;

    private final String id = "orderId";

    private final OrderSide side = OrderSide.BUY;

    // mock order
    private final MarketOrder mockOrder = Mockito.mock(MarketOrder.class);

    private final double quantity = 10.5;

    private final double price = 10;
    private final OrderType type = OrderType.MARKET;

    @Override
    public void init() {
        Mockito.when(this.orderBook.findOrderById(this.id, this.side))
                .thenReturn(this.mockOrder);
    }

    /**
     * Get orders test.
     */
    @Test
    public void getOrders() {

        Map<String, List<Order>> result = this.orderService.getOrders();

        // check if OrderBook.getBuyOrders() was invoked
        Mockito.verify(this.orderBook).getBuyOrders();
        // check if OrderBook.getSellOrders() was invoked
        Mockito.verify(this.orderBook).getSellOrders();

        assertTrue(result.containsKey("BUY"));
        assertTrue(result.containsKey("SELL"));
    }

    /**
     * Get order by ID test.
     */
    @Test
    public void testGetOrderById() {
        Map<String, Order> result = this.orderService.getOrder(this.id,
                this.side);
        assertTrue(result.containsKey("order"));
        assertEquals(result.get("order"), this.mockOrder);
    }

    
    @Test
    public void testAddOrder() {
        List<Trade> trades = new ArrayList<>();

        // make process() return empty list
        Mockito.when(this.orderBook.process(Mockito.any())).thenReturn(trades);

        Map<String, Object> result = this.orderService.addOrder(this.side,
                this.quantity, this.price, this.type);

        assertTrue(result.containsKey(this.id));
        assertTrue(result.containsKey("result"));
        assertEquals(result.get("result"), trades);
    }



}
