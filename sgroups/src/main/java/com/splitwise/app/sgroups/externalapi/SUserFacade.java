package com.splitwise.app.sgroups.externalapi;

import com.splitwise.app.sgroups.vo.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Base64;

@Component
public class SUserFacade {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RestTemplate restTemplate;


    public boolean checkUsername(String tempUsername) {

        String uri = "http://" + "SUSER-SERVICE" + "/sw/users/getUserDetails/";
        log.info(uri + tempUsername);
        ResponseEntity<UserResponse> userDetail = restTemplate.exchange(uri + tempUsername, HttpMethod.GET, new HttpEntity<>(httpHeader()), UserResponse.class);
        log.info("The response of the service call is--> " + userDetail);
        if (userDetail.getBody().getUsername() != null)
            return true;
        else
            return false;

    }

    private HttpHeaders httpHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        // httpHeaders.add("Authorization", "Basic "+getBasicAuthHeader());
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }

    private String getBasicAuthHeader() {
        String creds = "USER1:pass";
        return new String(Base64.getEncoder().encode(creds.getBytes()));
    }


}
