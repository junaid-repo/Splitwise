package com.splitwise.app.sgroups.controller;

import com.splitwise.app.sgroups.dto.SaveGroupResponse;
import com.splitwise.app.sgroups.dto.GroupDetails;
import com.splitwise.app.sgroups.entites.GroupMembers;
import com.splitwise.app.sgroups.service.GroupServices;
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
    ResponseEntity<SaveGroupResponse> createGroup(@RequestBody GroupDetails request) {

        SaveGroupResponse response = new SaveGroupResponse();

        response = serv.saveGroup(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);


    }
    @GetMapping("/getGroupMembers/{groupName}")
    ResponseEntity<List<GroupMembers>> getGroupDetails(@PathVariable String groupName){

        List<GroupMembers> response= new ArrayList<>();
        response=serv.getGroupDetails(groupName);

        return ResponseEntity.status(HttpStatus.FOUND).body(response);

    }

}
