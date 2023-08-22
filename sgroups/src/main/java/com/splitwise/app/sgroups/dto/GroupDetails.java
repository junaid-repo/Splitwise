package com.splitwise.app.sgroups.dto;

import com.splitwise.app.sgroups.dto.UserNames;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class GroupDetails {

    private String groupName;
    private String groupUserName;
    private List<UserNames> userList;
    private String adminUserName;
}
