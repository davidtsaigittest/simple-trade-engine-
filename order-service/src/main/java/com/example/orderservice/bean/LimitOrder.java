package com.example.orderservice.bean;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/*

 */
@Data
public class LimitOrder extends Order implements Comparable<LimitOrder> {


    public LimitOrder(double quantity, double price, String id, Side side, Date currentTime) {
        super(quantity, price, id, side, currentTime);
    }

    // Builder外部調用，用來建立order
    public static class Builder {

       
        private double quantity;

        
        private double price;

        
        private final Side side;

        
        private Date currentTime;

        
        private String id;

       
        public Builder(Side side) {
            this.side = side;
        }

        public Builder withQuantity(double quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder atPrice(double pPrice) {
            this.price = pPrice;
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
        public LimitOrder build() {
            return new LimitOrder(this.quantity, this.price,
                    Optional.ofNullable(this.id), this.side,
                    Optional.ofNullable(this.currentTime));
        }

    }

    
    private LimitOrder(double quantity, double price,
                       Optional<String> id, final Side side,
                       Optional<Date> currentTime) {

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

    }

   

    
    @Override
    public int compareTo(LimitOrder order) {

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
