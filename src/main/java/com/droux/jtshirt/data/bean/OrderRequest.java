package com.droux.jtshirt.data.bean;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Date orderDate;
    private String clientName;
    List<OrderRequestItem> items;
}
