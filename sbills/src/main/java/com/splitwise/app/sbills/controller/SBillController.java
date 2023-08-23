package com.splitwise.app.sbills.controller;

import com.splitwise.app.sbills.dto.BaseOutput;
import com.splitwise.app.sbills.dto.SplitBillRequest;
import com.splitwise.app.sbills.service.BillService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
