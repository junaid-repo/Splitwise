package com.splitwise.app.sbills.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberWise {
	
	private String memberName;
	private Double toGive=0d;
	private Double toTake=0d;

}
