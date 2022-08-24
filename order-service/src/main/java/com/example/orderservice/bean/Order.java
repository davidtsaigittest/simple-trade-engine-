package com.example.orderservice.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 MarketOrder 立即以當前市價買進或賣出(Order filled immediately)
 LimitOrder 非立即性買進或賣出，待未來市價到達期望值，才會被賣出(Order filled in future)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Order {

    private double quantity;

    private double price;

    private String id;

    private OrderSide side;

    private OrderType type;

    private Date currentTime;

}
