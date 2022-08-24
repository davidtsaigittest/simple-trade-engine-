package com.example.orderservice.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderRequest {

    private String orderId;

    private Double quantity;

    private Double price;

    private String side;

    private String orderType;

}
