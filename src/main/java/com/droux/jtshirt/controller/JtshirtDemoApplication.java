package com.droux.jtshirt.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.droux.jtshirt.data")
public class JtshirtDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JtshirtDemoApplication.class, args);
	}
}
