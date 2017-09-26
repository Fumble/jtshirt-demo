package com.droux.jtshirt.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.droux.jtshirt.data.bean.Tshirt;

@Repository
public interface TshirtRepository extends CrudRepository<Tshirt, Long> {

}