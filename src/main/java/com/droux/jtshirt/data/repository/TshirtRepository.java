package com.droux.jtshirt.data.repository;

import com.droux.jtshirt.data.bean.Tshirt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TshirtRepository extends CrudRepository<Tshirt, Long> {

}