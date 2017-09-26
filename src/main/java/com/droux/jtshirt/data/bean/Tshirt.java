package com.droux.jtshirt.data.bean;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.droux.jtshirt.controller.form.TshirtForm;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Tshirt() {
    }

    public Tshirt(TshirtForm form) {
        this.id = form.getId();
        this.name = form.getName();
        this.size = form.getSize();
        this.color = form.getColor();
        this.price = form.getPrice();
        this.image = form.getImage();
        this.quantity = form.getQuantity();
    }

    public Tshirt(String name, String size, String color, BigDecimal price, String image, Integer quantity) {
        this.name = name;
        this.size = size;
        this.color = color;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
    }
}
