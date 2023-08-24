package com.splitwise.app.sbills.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class LogMessageDetails {
	
	LocalDateTime date;
	String message;
	

}
