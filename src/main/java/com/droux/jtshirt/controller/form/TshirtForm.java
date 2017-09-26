package com.droux.jtshirt.controller.form;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import com.droux.jtshirt.data.bean.Tshirt;

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
    private MultipartFile imageFile;

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
        this.price = BigDecimal.ZERO;
        this.quantity = 0;
    }

    public TshirtForm(Tshirt bean) {
        this.id = bean.getId();
        this.name = bean.getName();
        this.size = bean.getSize();
        this.color = bean.getColor();
        this.price = bean.getPrice();
        this.image = bean.getImage();
        this.quantity = bean.getQuantity();
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}
