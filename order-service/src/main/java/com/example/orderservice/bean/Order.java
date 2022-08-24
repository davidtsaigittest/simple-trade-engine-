package com.example.orderservice.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Order {

    private double quantity;


    private double price;


    private String id;


    private Side side;


    private Date currentTime;

}
