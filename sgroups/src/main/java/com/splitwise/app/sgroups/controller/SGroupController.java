package com.splitwise.app.sgroups.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sw/groups")
public class SGroupController {
	
	/*
	 * @PostMapping("/createGroup") ResponseEntity<BaseOutput>
	 * createOneGroup(@RequestBody )
	 */
	@GetMapping("/welcome")
	ResponseEntity<String> justChecking(){
		return new ResponseEntity<>("Welcome to group service", HttpStatus.OK);
	}

}
