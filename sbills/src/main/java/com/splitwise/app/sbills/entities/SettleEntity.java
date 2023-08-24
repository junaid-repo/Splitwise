package com.splitwise.app.sbills.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="App_Settle_outstandings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SettleEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private LocalDateTime updatedDate;
	private String yourName;
	private String toMember;
	private String settleInd;

}
