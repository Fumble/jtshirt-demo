package com.droux.jtshirt.data.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Date orderDate;
    private String clientName;
    private BigDecimal totalAmount;
    @OneToMany(mappedBy = "order" ,fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderItem> items;
}