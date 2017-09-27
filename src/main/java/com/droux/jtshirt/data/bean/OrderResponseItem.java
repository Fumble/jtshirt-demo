package com.droux.jtshirt.data.bean;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseItem {
    private Long id;
    private int quantity;
    private BigDecimal price;
    private String status;

    public OrderResponseItem(OrderRequestItem ori) {
        this.id = ori.getId();
        this.quantity = ori.getQuantity();
        this.status = "OK";
    }
}
