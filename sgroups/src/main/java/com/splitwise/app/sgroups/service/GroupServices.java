package com.splitwise.app.sgroups.service;


import com.splitwise.app.sgroups.dto.SaveGroupResponse;
import com.splitwise.app.sgroups.dto.GroupDetails;
import com.splitwise.app.sgroups.entites.GroupEntity;
import com.splitwise.app.sgroups.entites.GroupMembers;
import com.splitwise.app.sgroups.externalapi.SUserFacade;
import com.splitwise.app.sgroups.repository.GroupBasicSaveRepository;
import com.splitwise.app.sgroups.repository.GroupMemberSaveRepository;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class GroupServices {

    @Autowired
    GroupBasicSaveRepository groupBasicRepo;

    @Autowired
    GroupMemberSaveRepository groupMemSave;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SUserFacade comm;


    public SaveGroupResponse saveGroup(GroupDetails request) {
        SaveGroupResponse response = new SaveGroupResponse();
        int flag = 0;
        // GroupMembers gm = new GroupMembers();
        GroupEntity ge = new GroupEntity();
        GroupEntity geOut = new GroupEntity();


        LocalDateTime updatedDate = LocalDateTime.now();

        log.info("Validating the username with that of system");
        List<SaveGroupResponse> checkList = request.getUserList().stream().map(user -> {
                    String tempUsername = user.getUsername();

                    boolean check = comm.checkUsername(tempUsername);
                    if (check == false) {

                        response.setReturnCode("404");
                        response.setReturnMsg("User " + tempUsername + " not found in System, please try valid users");
                        return response;

                    } else {
                        response.setReturnCode("201");
                        response.setReturnMsg("Valid");
                    }
                    return response;
                }
        ).collect(Collectors.toList());


        for (SaveGroupResponse tempCheckList : checkList) {
            if (tempCheckList.getReturnCode().equals("404"))
                return tempCheckList;
        }
        ge.setGroupName(request.getGroupName());
        ge.setCreatedDate(updatedDate);
        ge.setAdminName(request.getAdminUserName());

        log.info("Saving basic details of group with request --> " + ge);
        geOut = groupBasicRepo.save(ge);

        String groupUserName = ge.getGroupName().replace(" ", "_");
        groupUserName = groupUserName.toLowerCase() + String.valueOf(geOut.getId());

        ge.setGroupUsername(groupUserName);
        geOut = groupBasicRepo.save(ge);

        log.info("Saving basic details of group ends here");

        log.info("Saving of members starts here ");
        //final String gun=  groupUserName;

        String finalGroupUserName = groupUserName;
        List<GroupMembers> gmList = request.getUserList().stream().map(users -> {
            GroupMembers gmOut = new GroupMembers();
            GroupMembers gm = new GroupMembers();
            gm.setGroupId(ge.getId());
            gm.setUpdatedDate(updatedDate);
            gm.setGroupUsername(finalGroupUserName);
            gm.setMemberUsername(users.getUsername());
            gmOut = groupMemSave.save(gm);
            return gmOut;

        }).collect(Collectors.toList());

        if (gmList.size() > 1) {
            response.setGroupName(groupUserName);
            response.setReturnCode("201");
            response.setReturnMsg("Group created");


        } else {
            response.setReturnCode("432");
            response.setReturnMsg("Something went wrong");
        }


        return response;
    }

    public  List<GroupMembers> getGroupDetails(String groupName) {
        List<GroupMembers> response= new ArrayList<>();

        response=groupMemSave.findByUsername(groupName);

        return response;
    }
}
