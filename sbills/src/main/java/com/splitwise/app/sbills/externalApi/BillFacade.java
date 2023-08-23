package com.splitwise.app.sbills.externalApi;

import com.splitwise.app.sbills.vo.GroupMembers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class BillFacade {

    @Autowired
    RestTemplate template;
    Logger log = LoggerFactory.getLogger(this.getClass());
    public List<GroupMembers> getGroupMembersList(String groupName) {
        //List<GroupMembers> memberList= new ArrayList<>();

        String uri = "http://" + "SGROUPS-SERVICE" + "/sw/groups/getGroupMembers/";
        log.info(uri + groupName);
        ResponseEntity<List<GroupMembers>> memberList = template.exchange(uri + groupName, HttpMethod.GET, new HttpEntity<>(httpHeader()), new ParameterizedTypeReference<List<GroupMembers>>() {});
        log.info("The response of the service call is--> " + memberList);

        return memberList.getBody();

    }

    private HttpHeaders httpHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        // httpHeaders.add("Authorization", "Basic "+getBasicAuthHeader());
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }
}
