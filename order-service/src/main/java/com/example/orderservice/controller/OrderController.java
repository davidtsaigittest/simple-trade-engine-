package com.example.orderservice.controller;


import com.example.orderservice.bean.LimitOrder;
import com.example.orderservice.bean.Order;
import com.example.orderservice.bean.Side;
import com.example.orderservice.bean.OrderRequest;
import com.example.orderservice.service.IOrderService;
import com.example.orderservice.service.IOrderSideService;
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
        return orderService.addOrder(Side.valueOf(request.getSide()), request.getQuantity(), request.getPrice());
    }

    @GetMapping("/getall")
    public Map<String, List<Order>> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/getbyid")
    public Map<String, Order> getOrderById(@RequestBody OrderRequest request) {
        return orderService.getOrder(request.getOrderId(), Side.valueOf(request.getSide()));
    }




}
