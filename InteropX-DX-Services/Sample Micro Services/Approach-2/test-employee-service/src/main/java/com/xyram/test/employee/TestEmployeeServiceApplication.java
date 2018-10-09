package com.xyram.test.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TestEmployeeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestEmployeeServiceApplication.class, args);
	}
}
