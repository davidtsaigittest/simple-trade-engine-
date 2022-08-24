package com.example.orderservice.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Trade {

    private String buyOrderId;

    private String sellOrderId;

    private double quantity;

    private double price;

}
