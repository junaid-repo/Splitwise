package com.splitwise.app.sbills;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SbillsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbillsApplication.class, args);
	}

}
