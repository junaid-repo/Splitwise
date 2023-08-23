package com.splitwise.app.sbills.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SplitBillRequest {

    private String groupName;
    private String paidBy;
    private Double amount;
    private String remarks;
}
