package com.splitwise.app.sgroups.entites;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="GroupMembers")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GroupMembers {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String groupUsername;
    private Integer groupId;
    private String memberUsername;
    private LocalDateTime updatedDate;
}
