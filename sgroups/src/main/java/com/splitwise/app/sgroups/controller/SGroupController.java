package com.splitwise.app.sgroups.controller;

import com.splitwise.app.sgroups.dto.SaveGroupResponse;
import com.splitwise.app.sgroups.dto.GroupDetails;
import com.splitwise.app.sgroups.entites.GroupMembers;
import com.splitwise.app.sgroups.service.GroupServices;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sw/groups")
public class SGroupController {

    @Autowired
    GroupServices serv;

    @GetMapping("/welcome")
    ResponseEntity<String> justChecking() {
        return new ResponseEntity<>("Welcome to group service", HttpStatus.OK);
    }

    @PostMapping("/createGroup")
    @CircuitBreaker(name = "createGroupCB", fallbackMethod = "fallbackMethodForCreateGroup")
    ResponseEntity<SaveGroupResponse> createGroup(@RequestBody GroupDetails request) {

        SaveGroupResponse response = new SaveGroupResponse();

        response = serv.saveGroup(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);


    }

    @GetMapping("/getGroupMembers/{groupName}")
    ResponseEntity<List<GroupMembers>> getGroupDetails(@PathVariable String groupName) {

        List<GroupMembers> response = new ArrayList<>();
        response = serv.getGroupDetails(groupName);

        return ResponseEntity.status(HttpStatus.FOUND).body(response);

    }

   public ResponseEntity<SaveGroupResponse> fallbackMethodForCreateGroup(GroupDetails request, Exception ex) {
        SaveGroupResponse fmr = new SaveGroupResponse();
        ex.printStackTrace();
        fmr.setReturnMsg("The calling service is not active so breaking the flow");
        fmr.setReturnCode("503");
        return new ResponseEntity<>(fmr,HttpStatus.BAD_GATEWAY);
    }
}