package com.example.docker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	
	@GetMapping("/testGetWithDocker")
	ResponseEntity<String> testDocker(){
		
		return ResponseEntity.status(HttpStatus.CREATED).body("It is working");
	}

}
