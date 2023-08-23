package com.splitwise.app.sbills.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GroupExpenseAccounts {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String groupname;
    String giver;
    String takers;
    Double amount=0d;
    LocalDateTime createdDate;
    String eventName;
    String settledInd;

}
