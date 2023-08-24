package com.splitwise.app.sbills.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.splitwise.app.sbills.dto.BaseOutput;
import com.splitwise.app.sbills.dto.DashboardDetails;
import com.splitwise.app.sbills.dto.SplitBillRequest;
import com.splitwise.app.sbills.dto.UserLogResponse;
import com.splitwise.app.sbills.entities.SettleEntity;
import com.splitwise.app.sbills.service.BillService;

@RestController
@RequestMapping("/sw/bills")
public class SBillController {

    @Autowired
    BillService serv;

    @PostMapping("/splitBills")
    ResponseEntity<BaseOutput> splitBill(@RequestBody SplitBillRequest req){

        BaseOutput response = new BaseOutput();

        response=serv.splitBill(req);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);



    }
    @GetMapping("/dashboardDetails/{username}")
   ResponseEntity <DashboardDetails> getDashboardDetails(@PathVariable String username){
    	DashboardDetails response = new DashboardDetails();
    	
    	response=serv.getDashboardDetails(username);
    	return new ResponseEntity(response, HttpStatus.FOUND);    	
    }
    @PostMapping("/settleOutstandings")
    ResponseEntity<BaseOutput> settleAmt(@RequestBody SettleEntity ent){
    	
    	BaseOutput response= new BaseOutput();
    	response=serv.settleAmt(ent);
    	
    	return new ResponseEntity(response, HttpStatus.FOUND);
    	
    }
    @GetMapping("/history/{username}")
    ResponseEntity<UserLogResponse> userWiseLogs(@PathVariable String username){
    	
    	UserLogResponse response = new UserLogResponse();
    	response=serv.getUserLogs(username);
    	
    	return new ResponseEntity(response, HttpStatus.FOUND);
    	
    }

}
