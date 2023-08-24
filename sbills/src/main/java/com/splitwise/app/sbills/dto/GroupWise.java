package com.splitwise.app.sbills.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GroupWise {
	
	private String groupName;
	private Double toGive=0d;
	private Double toTake=0d;

}
