package com.splitwise.app.sgroups.externalapi;

import com.splitwise.app.sgroups.vo.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Base64;

@Component
public class SUserFacade {

    @Autowired
    RestTemplate restTemplate;


    public boolean checkUsername(String tempUsername) {

        String uri="http://"+"SUSER-SERVICE"+"/user/getUserDetails/";
        ResponseEntity<UserResponse> userDetail=restTemplate.exchange(uri+tempUsername, HttpMethod.GET, new HttpEntity<>(httpHeader()), UserResponse.class);

    if (userDetail!=null)
                return true;
    else
       return false;

    }

    private HttpHeaders httpHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
       // httpHeaders.add("Authorization", "Basic "+getBasicAuthHeader());
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }

    private String getBasicAuthHeader(){
        String creds="USER1:pass";
        return new String(Base64.getEncoder().encode(creds.getBytes()));
    }


}
