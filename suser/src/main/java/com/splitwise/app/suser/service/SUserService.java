package com.splitwise.app.suser.service;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.splitwise.app.suser.cloud.EmailSender;
import com.splitwise.app.suser.dto.MessageDTO;
import com.splitwise.app.suser.dto.UserResponse;
import com.splitwise.app.suser.entity.SUserEntity;
import com.splitwise.app.suser.repository.SUserSaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SUserService {

	@Autowired
	SUserSaveRepository userRepo;

	@Autowired
	EmailSender emailSender;

	public UserResponse createUser(SUserEntity req) {
		UserResponse response = new UserResponse();
		SUserEntity out = new SUserEntity();

		LocalDateTime createdDate = LocalDateTime.now();
		req.setCreatedDate(createdDate);
		req.setRole("USER");
		out = userRepo.save(req);

		// generating username
		String username = req.getFirstName().toLowerCase() + req.getLastName().toLowerCase() + "0"
				+ String.valueOf(out.getId());
		// ends
		req.setUsername(username);
		out = userRepo.save(req);

		if (out.getId() < 1) {
			response.setReturnCode("334");
			response.setReturnMsg("something went wrong!!! Please try again later");
		}
		response.setReturnCode(String.valueOf(HttpStatus.CREATED.value()));
		response.setReturnMsg(HttpStatus.CREATED.toString());
		response.setUsername(username);
		return response;
	}

    public UserResponse getByUsername(String username) {
		UserResponse response = new UserResponse();
		SUserEntity retOut = new SUserEntity();
		retOut= userRepo.findbyUsername(username);
		if(retOut!=null) {
			response.setUsername(retOut.getUsername());
			response.setReturnMsg("Found");
			response.setReturnCode("200");
		}
		else{
			response.setReturnMsg("Not Found");
			response.setReturnCode("404");
		}

		return response;
    }

	public void sendEmail(MessageDTO messageDTO) {
		SUserEntity userDetails= userRepo.findbyUsername(messageDTO.getUsername());
		String fromEmailId="tahanasim3001@gmail.com";
        try {
            emailSender.sendEmail(userDetails.getEmail(),fromEmailId,userDetails.getFirstName()+" "+userDetails.getLastName(), "SplitWise",
                    messageDTO.getEventCode(), messageDTO.getMessage());
        } catch (MailjetException e) {
            throw new RuntimeException(e);
        } catch (MailjetSocketTimeoutException e) {
            throw new RuntimeException(e);
        }

    }
}
