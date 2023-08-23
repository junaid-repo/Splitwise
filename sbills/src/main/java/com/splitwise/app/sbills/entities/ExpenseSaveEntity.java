package com.splitwise.app.sbills.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name ="expense_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ExpenseSaveEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String groupName;
    private String remarks;
    LocalDateTime updatedDate;
}
