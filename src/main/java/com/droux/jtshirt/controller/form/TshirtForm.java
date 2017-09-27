package com.droux.jtshirt.controller.form;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import com.droux.jtshirt.data.bean.Tshirt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public TshirtForm(Tshirt bean) {
        this.id = bean.getId();
        this.name = bean.getName();
        this.size = bean.getSize();
        this.color = bean.getColor();
        this.price = bean.getPrice();
        this.image = bean.getImage();
        this.quantity = bean.getQuantity();
    }
}
