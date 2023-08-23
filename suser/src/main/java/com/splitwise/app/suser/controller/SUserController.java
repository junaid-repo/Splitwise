package com.splitwise.app.suser.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.splitwise.app.suser.dto.UserResponse;
import com.splitwise.app.suser.entity.SUserEntity;
import com.splitwise.app.suser.service.SUserService;

@RestController
@RequestMapping("/sw/users")
public class SUserController {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	SUserService serv;

	@PostMapping("/createAccount")
	ResponseEntity<UserResponse> createOneUser(@RequestBody SUserEntity req) {
		UserResponse response = new UserResponse();

		response = serv.createUser(req);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/getUserDetails/{username}")
	ResponseEntity<UserResponse> findByUsername(@PathVariable String username) {
		UserResponse response = new UserResponse();
		log.info("Entering into SUserController with username --> " + username);
		response = serv.getByUsername(username);

		return ResponseEntity.status(HttpStatus.FOUND).body(response);

	}

}
