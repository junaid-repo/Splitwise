package com.splitwise.app.sbills.service;

import com.splitwise.app.sbills.dto.BaseOutput;
import com.splitwise.app.sbills.dto.SplitBillRequest;
import com.splitwise.app.sbills.entities.ExpenseSaveEntity;
import com.splitwise.app.sbills.entities.GroupExpenseAccounts;
import com.splitwise.app.sbills.entities.LogDetailsEntity;
import com.splitwise.app.sbills.externalApi.BillFacade;
import com.splitwise.app.sbills.repository.ExpenseSaveRepository;
import com.splitwise.app.sbills.repository.GroupExSaveRepository;
import com.splitwise.app.sbills.repository.LogSaveRepository;
import com.splitwise.app.sbills.vo.GroupMembers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BillService {

    @Autowired
    ExpenseSaveRepository exSaveRepo;

    @Autowired
    GroupExSaveRepository groupExSave;

    @Autowired
    LogSaveRepository logRepo;

    @Autowired
    BillFacade comm;

    public BaseOutput splitBill(SplitBillRequest req) {
        ExpenseSaveEntity ese = new ExpenseSaveEntity();
        LocalDateTime updatedDate = LocalDateTime.now();

        ese.setUpdatedDate(updatedDate);
        ese.setGroupName(req.getGroupName());
        ese.setRemarks(req.getRemarks());
        ese = exSaveRepo.save(ese);

        List<GroupMembers> membersList = comm.getGroupMembersList(req.getGroupName());
        int noOfMembers = membersList.size();
        Double eachOneContribution = req.getAmount() / noOfMembers;

        membersList.stream().forEach(obj -> {
            String eventName = "";
            LocalDateTime ldt = LocalDateTime.now();
            String stringTime = ldt.toString();
            stringTime = stringTime.replace(' ', '0');
            String giver = "";
            String taker = "";
            String memberUsername = obj.getMemberUsername();
            taker = obj.getMemberUsername();
            GroupExpenseAccounts gea = new GroupExpenseAccounts();
            Double eachOneContri = (double) (Math.round(eachOneContribution));
            gea.setAmount(eachOneContri);
            gea.setCreatedDate(ldt);
            gea.setGiver(req.getPaidBy());
            gea.setTakers(taker);
            gea.setGroupname(req.getGroupName());
            gea.setEventName("SAVE");
            groupExSave.save(gea);
            final String logMem = obj.getMemberUsername();

            Runnable rn = new Runnable() {
                @Override
                public void run() {
                    LocalDateTime updateDate = LocalDateTime.now();
                    LogDetailsEntity logDetails = new LogDetailsEntity();
                    logDetails.setCreatedDate(ldt);
                    logDetails.setUsername(logMem);
                    logDetails.setGroupName(req.getGroupName());
                    String message = "You owe " + String.valueOf(Math.round(eachOneContri)) + " to user " + req.getPaidBy() + " in group " + req.getGroupName();

                    logDetails.setMessage(message);
                    if (req.getPaidBy().equals(logMem) == false) {
                        logDetails = logRepo.save(logDetails);
                    }
                }
            };
            Thread th = new Thread(rn);
            th.start();

        });
        LocalDateTime date = LocalDateTime.now();
        LogDetailsEntity logDetails = new LogDetailsEntity();
        logDetails.setCreatedDate(date);
        logDetails.setGroupName(req.getGroupName());
        logDetails.setUsername(req.getPaidBy());
        logDetails.setMessage("You added " + req.getAmount() + " in group " + req.getGroupName());
        logRepo.save(logDetails);
        BaseOutput response = new BaseOutput();
        response.setReturnCode("201");
        response.setReturnMsg("Updated");


        return response;
    }
}
