package com.example.orderservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.example.orderservice.bean.Order;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

import com.example.orderservice.bean.LimitOrder;
import com.example.orderservice.module.OrderBook;
import com.example.orderservice.bean.Side;
import com.example.orderservice.bean.Trade;
import com.example.orderservice.service.OrderService;


public class OrderServiceTests extends AbstractTest {

    /**
     * Mock order book.
     */
    @Mock
    private OrderBook orderBook;

    /**
     * Event publisher.
     */
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * Controller under test.
     */
    @InjectMocks
    @Resource
    private OrderService orderService;

    // captor
    @Captor
    private ArgumentCaptor<ApplicationEvent> captor;

    private final String id = "orderId";

    private final Side side = Side.BUY;

    // mock order
    private final LimitOrder mockOrder = Mockito.mock(LimitOrder.class);

    private final double quantity = 10.5;

    private final double price = 10;

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

        Mockito.verify(this.orderBook).getBuyOrders();
        Mockito.verify(this.orderBook).getSellOrders();

        assertTrue(result.containsKey("BUY"));
        assertTrue(result.containsKey("SELL"));
    }

    /**
     * Get order by ID test.
     */
    @Test
    public void getOrderById() {
        Map<String, Order> result = this.orderService.getOrder(this.id,
                this.side);
        assertTrue(result.containsKey("order"));
        assertEquals(result.get("order"), this.mockOrder);
    }

    
    @Test
    public void addOrderTest() {
        List<Trade> trades = new ArrayList<>();
        
        Mockito.when(this.orderBook.process(Mockito.any())).thenReturn(trades);

        Map<String, Object> result = this.orderService.addOrder(this.side,
                this.quantity, this.price);

        assertTrue(result.containsKey(this.id));
        assertTrue(result.containsKey("result"));
        assertEquals(result.get("result"), trades);
    }

}
