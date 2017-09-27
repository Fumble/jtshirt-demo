package com.droux.jtshirt.data.bean;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Long tshirtId;
    private String name;
    private String size;
    private String color;
    private BigDecimal price;
    private Integer quantity;
    @ManyToOne(cascade = CascadeType.ALL)
    private Orders order;

    public OrderItem(Tshirt tshirt) {
        this.tshirtId = tshirt.getId();
        this.name = tshirt.getName();
        this.size = tshirt.getSize();
        this.color = tshirt.getColor();
        this.price = tshirt.getPrice();
    }
}
