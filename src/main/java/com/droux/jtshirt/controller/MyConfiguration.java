package com.droux.jtshirt.controller;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.droux.jtshirt.data.repository"})
@EntityScan(basePackages = {"com.droux.jtshirt.data.bean", "com.droux.jtshirt.controller.form"})
public class MyConfiguration {

}
