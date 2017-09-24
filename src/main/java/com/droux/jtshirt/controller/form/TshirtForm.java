package com.droux.jtshirt.controller.form;

import com.droux.jtshirt.data.bean.Tshirt;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class TshirtForm {
    private Long id;
    @NotBlank
    @Size(min = 2, max = 255)
    private String name;
    @NotBlank
    private String size;
    @NotBlank
    private String color;
    @NotNull
    @Min(0)
    private BigDecimal price;
    private String image;
    @NotNull
    @Min(0)
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

    public TshirtForm(){

    }

    public TshirtForm(Tshirt form) {
        this.id = form.getId();
        this.name = form.getName();
        this.size = form.getSize();
        this.color = form.getColor();
        this.price = form.getPrice();
        this.image = form.getImage();
        this.quantity = form.getQuantity();
    }
}
