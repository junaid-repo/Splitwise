package com.splitwise.app.suser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.splitwise.app.suser.dto.UserResponse;
import com.splitwise.app.suser.entity.SUserEntity;
import com.splitwise.app.suser.service.SUserService;

@RestController
@RequestMapping("/sw/users")
public class SUserController {
	
	@Autowired
	SUserService serv;

	@PostMapping("/createAccount")
	ResponseEntity<UserResponse> createOneUser(@RequestBody SUserEntity req) {
		UserResponse response = new UserResponse();

		response = serv.createUser(req);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

}
