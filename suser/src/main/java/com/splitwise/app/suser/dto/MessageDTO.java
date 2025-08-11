package com.splitwise.app.suser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {


    private Integer id;

    LocalDateTime createdDate;
    String username;
    String groupName;
    String message;
    String eventCode;
}
