package com.splitwise.app.sgroups.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SaveGroupResponse extends  BaseOutput{
    private String groupName;
}
