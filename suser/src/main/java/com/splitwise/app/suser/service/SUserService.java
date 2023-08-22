package com.splitwise.app.suser.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.splitwise.app.suser.dto.UserResponse;
import com.splitwise.app.suser.entity.SUserEntity;
import com.splitwise.app.suser.repository.SUserSaveRepository;

@Service
public class SUserService {

	@Autowired
	SUserSaveRepository userRepo;

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

}
