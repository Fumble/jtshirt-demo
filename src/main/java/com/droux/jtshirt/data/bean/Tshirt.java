package com.droux.jtshirt.data.bean;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.droux.jtshirt.controller.form.TshirtForm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor()
@AllArgsConstructor()
@Entity
public class Tshirt {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String size;
    private String color;
    private BigDecimal price;
    private String image;
    private Integer quantity;

    public Tshirt(TshirtForm form) {
        this.id = form.getId();
        this.name = form.getName();
        this.size = form.getSize();
        this.color = form.getColor();
        this.price = form.getPrice();
        this.image = form.getImage();
        this.quantity = form.getQuantity();
    }
}
