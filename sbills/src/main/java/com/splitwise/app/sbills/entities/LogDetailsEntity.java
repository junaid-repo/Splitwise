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
public class LogDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id;

    LocalDateTime createdDate;
    String username;
    String groupName;
    String message;
}
