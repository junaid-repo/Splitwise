package com.splitwise.app.sgroups.entites;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name="GroupBasics")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String groupUsername;

    private LocalDateTime createdDate;
    private String adminName;
    private String groupName;
}
