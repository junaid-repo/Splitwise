package com.splitwise.app.sgroups.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.splitwise.app.sgroups.dto.DashboardDetails;
import com.splitwise.app.sgroups.dto.GroupDetails;
import com.splitwise.app.sgroups.dto.SaveGroupResponse;
import com.splitwise.app.sgroups.entites.GroupMembers;
import com.splitwise.app.sgroups.service.GroupServices;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
@RequestMapping("/sw/groups")
public class SGroupController {

    @Autowired
    GroupServices serv;

    @GetMapping("/welcome")
    ResponseEntity<String> justChecking() {
        return new ResponseEntity<>("Welcome to group service", HttpStatus.OK);
    }

    int countRetry=0;
    @PostMapping("/createGroup")
   // @CircuitBreaker(name = "createGroupCB", fallbackMethod = "fallbackMethodForCreateGroup")
    //@Retry(name = "createGroupRetry", fallbackMethod = "fallbackMethodForCreateGroup")
    @RateLimiter(name = "createGroupRL", fallbackMethod = "fallbackMethodForCreateGroup")
    ResponseEntity<SaveGroupResponse> createGroup(@RequestBody GroupDetails request) {

        SaveGroupResponse response = new SaveGroupResponse();
        
       System.out.println("Retrying this number of time-->  "+countRetry);

        response = serv.saveGroup(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);


    }

    @GetMapping("/getGroupMembers/{groupName}")
    ResponseEntity<List<GroupMembers>> getGroupDetails(@PathVariable String groupName) {

        List<GroupMembers> response = new ArrayList<>();
        response = serv.getGroupDetails(groupName);

        return ResponseEntity.status(HttpStatus.FOUND).body(response);

    }
    
    @GetMapping("/dashboardDetails/{username}")
   ResponseEntity <DashboardDetails> getDashboardDetails(@PathVariable String username){
    	DashboardDetails response = new DashboardDetails();
    	
    	response=serv.getDashboardDetails(username);
    	return new ResponseEntity(response, HttpStatus.FOUND);    	
    }

   public ResponseEntity<SaveGroupResponse> fallbackMethodForCreateGroup(GroupDetails request, Exception ex) {
        SaveGroupResponse fmr = new SaveGroupResponse();
        ex.printStackTrace();
        fmr.setReturnMsg("The calling service is not active so breaking the flow");
        fmr.setReturnCode("503");
        return new ResponseEntity<>(fmr,HttpStatus.BAD_GATEWAY);
    }
}