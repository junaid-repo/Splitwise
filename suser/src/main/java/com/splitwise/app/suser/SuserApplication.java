package com.splitwise.app.suser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SuserApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuserApplication.class, args);
	}

}
