package com.splitwise.app.slogs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SlogsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlogsApplication.class, args);
	}

}
