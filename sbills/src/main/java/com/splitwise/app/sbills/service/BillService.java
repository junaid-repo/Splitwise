package com.splitwise.app.sbills.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.splitwise.app.sbills.dto.BaseOutput;
import com.splitwise.app.sbills.dto.DashboardDetails;
import com.splitwise.app.sbills.dto.GroupWise;
import com.splitwise.app.sbills.dto.LogMessageDetails;
import com.splitwise.app.sbills.dto.MemberWise;
import com.splitwise.app.sbills.dto.SplitBillRequest;
import com.splitwise.app.sbills.dto.UserLogResponse;
import com.splitwise.app.sbills.entities.ExpenseSaveEntity;
import com.splitwise.app.sbills.entities.GroupExpenseAccounts;
import com.splitwise.app.sbills.entities.LogDetailsEntity;
import com.splitwise.app.sbills.entities.SettleEntity;
import com.splitwise.app.sbills.externalApi.BillFacade;
import com.splitwise.app.sbills.externalApi.SQSUpdate;
import com.splitwise.app.sbills.repository.ExpenseSaveRepository;
import com.splitwise.app.sbills.repository.GroupExSaveRepository;
import com.splitwise.app.sbills.repository.LogSaveRepository;
import com.splitwise.app.sbills.repository.SettleSaveRepository;
import com.splitwise.app.sbills.vo.GroupMembers;

@Service
public class BillService {

	@Autowired
	ExpenseSaveRepository exSaveRepo;

	@Autowired
	GroupExSaveRepository groupExSave;
	
	@Autowired
	SettleSaveRepository esRepo;

	@Autowired
	LogSaveRepository logRepo;

	@Autowired
	BillFacade comm;
	
	@Autowired
	SQSUpdate sqsUpdate;

	Logger log = LoggerFactory.getLogger(this.getClass());

	public BaseOutput splitBill(SplitBillRequest req) {
		//ExpenseSaveEntity ese = new ExpenseSaveEntity();
		LocalDateTime updatedDate = LocalDateTime.now();

		var ese = ExpenseSaveEntity.builder()
				.updatedDate(LocalDateTime.now())
				.groupName(req.getGroupName())
				.remarks(req.getRemarks())
				.build();
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
			Double eachOneContri = (double) (Math.round(eachOneContribution));

			var gea = GroupExpenseAccounts.builder()
					.amount((double) Math.round(eachOneContribution))
					.createdDate(ldt)
					.giver(req.getPaidBy())
					.takers(taker)
					.groupname(req.getGroupName())
					.eventName("SAVE")
					.build();
			groupExSave.save(gea);
			final String logMem = obj.getMemberUsername();

			Runnable rn = new Runnable() {
				@Override
				public void run() {


					LogDetailsEntity logDetails = LogDetailsEntity.builder()
							.createdDate(ldt)
							.username(logMem)
							.groupName(req.getGroupName())
							.message("You owe " + String.valueOf(Math.round(eachOneContri)) + " to user "
									+ req.getPaidBy() + " in group " + req.getGroupName())
							.build();


					if (req.getPaidBy().equals(logMem) == false) {
						logDetails = logRepo.save(logDetails);
					}
				}
			};
			Thread th = new Thread(rn);
			th.start();

		});
		LocalDateTime date = LocalDateTime.now();
		var logDetails = LogDetailsEntity.builder()
				.createdDate(date)
				.groupName(req.getGroupName())
				.username(req.getPaidBy())
				.message("You added " + req.getAmount() + " in group " + req.getGroupName())
				.build();
		logRepo.save(logDetails);
		BaseOutput response = new BaseOutput();
		response.setReturnCode("201");
		response.setReturnMsg("Updated");

		return response;
	}

	public DashboardDetails getDashboardDetails(String username) {

		DashboardDetails response = new DashboardDetails();
		List<GroupWise> groupwiseDetails = new ArrayList<>();
		List<MemberWise> memberwise = new ArrayList<>();
		List<GroupExpenseAccounts> expenTakerList = new ArrayList<>();
		expenTakerList = groupExSave.findTakersbyUsername(username);

		List<String> distinctUsers = new ArrayList<>();
		Double totalAmt = 0d;

		for (GroupExpenseAccounts temp : expenTakerList) {
			if (distinctUsers.contains(temp.getTakers()) == false) {
				distinctUsers.add(temp.getTakers());
			}
		}
		for (GroupExpenseAccounts temp : expenTakerList) {
			if (distinctUsers.contains(temp.getGiver()) == false) {
				distinctUsers.add(temp.getGiver());
			}
		}

		log.info("the list of distinct users are " + distinctUsers);

		for (String tempUser : distinctUsers) {
			List<GroupExpenseAccounts> expenTakerList2 = new ArrayList<>();
			expenTakerList2 = groupExSave.findReceiverByUsername(tempUser, username);
			Double givenAmt = 0d;
			for (GroupExpenseAccounts tempAc : expenTakerList2) {
				givenAmt = givenAmt + tempAc.getAmount();
			}

			List<GroupExpenseAccounts> expenGiversList = new ArrayList<>();
			expenGiversList = groupExSave.findReceiverByUsername(username, tempUser);
			Double takenAmt = 0d;
			for (GroupExpenseAccounts tempAc : expenGiversList) {
				takenAmt = takenAmt + tempAc.getAmount();
			}

			MemberWise emd = new MemberWise();
			totalAmt = totalAmt - givenAmt;
			totalAmt = totalAmt + takenAmt;
			if (givenAmt > takenAmt) {
				emd.setToGive((double) Math.round(givenAmt - takenAmt));
			} else {
				emd.setToTake((double) Math.round(takenAmt - givenAmt));

			}
			emd.setMemberName(tempUser);
			memberwise.add(emd);

		}

		response.setTotalBalance((double) Math.round(totalAmt));
		response.setMemberwise(memberwise);
		List<GroupWise> groupExpenseDetails = new ArrayList<>();
		if (response != null) {
			groupExpenseDetails = getGroupWiseExpenseDetails(username);
			response.setGroupwiseDetails(groupExpenseDetails);
			
			response.setReturnCode(String.valueOf(HttpStatus.FOUND.value()));
			response.setReturnCode(String.valueOf(HttpStatus.FOUND));
		}

		return response;
	}

	private List<GroupWise> getGroupWiseExpenseDetails(String username) {
		List<GroupWise> response =  new ArrayList<>();
		
		List<String> getUserGroups= new ArrayList<>();
		
		final Double fnialAmount=0d;
		
		getUserGroups=groupExSave.getUserGroups(username);
		
		
		getUserGroups.stream().forEach(obj->{
			List<GroupExpenseAccounts> getAmountGivenToGroup= new ArrayList<>();
			List<GroupExpenseAccounts> getAmountTakenFromGroup = new ArrayList<>();
			
			getAmountGivenToGroup=groupExSave.amountGivenToGroup(obj,username);
			Double givenAmount=0d;
			for(GroupExpenseAccounts temp: getAmountGivenToGroup) {
				givenAmount=givenAmount+temp.getAmount();
			}
			
			getAmountTakenFromGroup=groupExSave.amoutTakenFromGroup(obj,username);
			Double takenAmount=0d;
			for(GroupExpenseAccounts temp: getAmountTakenFromGroup) {
				takenAmount=takenAmount+temp.getAmount();
			}
			Double finalAmt=(double) Math.round(givenAmount-takenAmount);
			
			GroupWise retObj= new GroupWise();
			retObj.setGroupName(obj);
			if(finalAmt>0) {
				retObj.setToTake(finalAmt);
			}
			else {
				retObj.setToGive(finalAmt);
			}
			response.add(retObj);
		});
		
		return response;
	}

	public BaseOutput settleAmt(SettleEntity ent) {
		
		BaseOutput response= new BaseOutput();
		Double totalAmount=0d;
		
		String self=ent.getYourName();
		String friend=ent.getToMember();
		
		List<GroupExpenseAccounts> expenTakerList2 = new ArrayList<>();
		expenTakerList2 = groupExSave.findReceiverByUsername(self, friend);
		Double givenAmt = 0d;
		for (GroupExpenseAccounts tempAc : expenTakerList2) {
			givenAmt = givenAmt + tempAc.getAmount();
		}

		List<GroupExpenseAccounts> expenGiversList = new ArrayList<>();
		expenGiversList = groupExSave.findReceiverByUsername(friend, self);
		Double takenAmt = 0d;
		for (GroupExpenseAccounts tempAc : expenGiversList) {
			takenAmt = takenAmt + tempAc.getAmount();
		}
		MemberWise emd= new MemberWise();
		totalAmount=totalAmount+givenAmt;
		totalAmount=totalAmount-takenAmt;
		
		if(totalAmount>-1) {
			response.setReturnCode("400");
			response.setReturnMsg(self+" do not owe any money to "+friend);
			return response;
		}else {
			List<GroupExpenseAccounts> expenseTakerList3= new ArrayList<>();
			
			try {
				groupExSave.updateTheOutstandingBalance(friend, self);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				groupExSave.updateTheOutstandingBalance2(self, friend);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			response.setReturnCode("200");
			response.setReturnMsg("Done");
			
			LocalDateTime date = LocalDateTime.now();
			var logDetails=  LogDetailsEntity.builder()
					.createdDate(date)
					.username(self)
					.groupName("Whole")
					.eventCode("PAID")
					.message("You paid all the outstanding amount to user " + friend)
					.build();
			logRepo.save(logDetails);

		var	logDetails2 = LogDetailsEntity.builder()
					.createdDate(date)
					.username(friend)
					.groupName("Whole")
					.eventCode("SETTLED")
					.message(self + " paid all the outstanding amount to you")
					.build();

			logDetails2=	logRepo.save(logDetails2);

			
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
            String jsonString;
			try {
				jsonString = objectMapper.writeValueAsString(logDetails2);
				sqsUpdate.sendMessage(jsonString);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			

			
			
			
		}
		
		Runnable rn = new Runnable() {
			@Override
			public void run() {
				SettleEntity ste =  new SettleEntity();
				LocalDateTime dt= LocalDateTime.now();
				ste = SettleEntity.builder()
						.updatedDate(dt)
						.settleInd("Y")
						.yourName(ent.getYourName())
						.toMember(ent.getToMember())
						.build();
				
			}
		};
		
		Thread th = new Thread(rn);
		th.start();
		
		
		
		return response;
	}
	
	public UserLogResponse getUserLogs(String username) {
		UserLogResponse response = new UserLogResponse();
		
		List<LogDetailsEntity> logDetails = new ArrayList<>();
		logDetails=logRepo.findLogs(username);
		
		List<LogMessageDetails> logs = new ArrayList<>();
		
		logDetails.stream().forEach(obj->{
			LogMessageDetails lmd= new LogMessageDetails();
			lmd.setDate(obj.getCreatedDate());
			lmd.setMessage(obj.getMessage());
			logs.add(lmd);
		});
		response.setLogs(logs);
		return response;
	}

}
