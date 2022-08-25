package com.example.orderservice.bean;

import lombok.Data;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;


@Data
public class MarketOrder extends Order implements Comparable<MarketOrder> {

    // Builder外部調用，用來建立order，不允許直接調用constructor
    public static class Builder {

       
        private double quantity;

        
        private double price;

        
        private final OrderSide side;

        
        private Date currentTime;

        
        private String id;

        private OrderType type;

        public Builder(OrderSide side) {
            this.side = side;
        }

        public Builder withQuantity(double quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder atPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder withType(OrderType type) {
            this.type = type;
            return this;
        }

        public Builder withId(String pId) {
            this.id = pId;
            return this;
        }

        public Builder withDateTime(Date dateTime) {
            this.currentTime = dateTime;
            return this;
        }

        /**
         * build()方法
         */
        public MarketOrder build() {
            return new MarketOrder(this.quantity, this.price,
                    Optional.ofNullable(this.id), this.side,
                    Optional.ofNullable(this.currentTime), this.type);
        }

    }

    
    private MarketOrder(double quantity, double price,
                        Optional<String> id, final OrderSide side,
                        Optional<Date> currentTime, OrderType type) {

        if (price <= 0) {
            throw new IllegalArgumentException(
                    "Prices must be greater than 0");
        } else if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than 0");
        }

        super.setQuantity(quantity);
        super.setPrice(price);
        super.setId(id.orElse(UUID.randomUUID().toString()));
        super.setSide(side);
        super.setCurrentTime(currentTime.orElse(new Date()));
        super.setType(type);
    }

   

    
    @Override
    public int compareTo(MarketOrder order) {

        if (Double.compare(this.getPrice(), order.getPrice()) == 0) {
            if (this.getCurrentTime().before(order.getCurrentTime())) {
                return -1;
            } else {
                return 1;
            }
        } else {
            return Double.compare(this.getPrice(), order.getPrice());
        }
    }

}
