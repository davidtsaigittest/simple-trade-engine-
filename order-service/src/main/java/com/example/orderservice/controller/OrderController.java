package com.example.orderservice.controller;


import com.example.orderservice.bean.Order;
import com.example.orderservice.bean.OrderType;
import com.example.orderservice.bean.OrderSide;
import com.example.orderservice.bean.OrderRequest;
import com.example.orderservice.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class OrderController {

    private IOrderService orderService;

    @Autowired
    public void setOrderService(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/add")
    public Map<String, Object> addOrder(@RequestBody OrderRequest request) {
        return orderService.addOrder(OrderSide.valueOf(request.getSide()), request.getQuantity(), request.getPrice(), OrderType.valueOf(request
                .getOrderType()));
    }

    @GetMapping("/getall")
    public Map<String, List<Order>> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/getbyid")
    public Map<String, Order> getOrderById(@RequestBody OrderRequest request) {
        return orderService.getOrder(request.getOrderId(), OrderSide.valueOf(request.getSide()));
    }




}
