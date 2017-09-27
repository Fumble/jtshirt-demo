package com.droux.jtshirt.data.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.droux.jtshirt.data.bean.Orders;

public interface OrderRepository extends CrudRepository<Orders, Long> {
    @Query("select o from Orders o where o.clientName = ?1 and o.id = ?2")
    Orders findByClientAndId(String client, Long id);
}
