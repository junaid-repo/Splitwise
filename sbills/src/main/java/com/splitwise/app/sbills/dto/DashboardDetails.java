package com.splitwise.app.sbills.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DashboardDetails extends BaseOutput{
	List<GroupWise> groupwiseDetails;
	List<MemberWise> memberwise;
	Double totalBalance=0d;
	

}
