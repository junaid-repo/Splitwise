package com.splitwise.app.suser.cloud;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.splitwise.app.suser.dto.MessageDTO;
import com.splitwise.app.suser.service.SUserService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class SQSConsume {

    private final SqsTemplate sqsTemplate;
    @Autowired
    SUserService sUserServ;

    public SQSConsume(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    @SqsListener("splitbill-emailQ")
    public void loadMessageFromSQS(String message) {
        log.info("message from SQS Queue {}", message);
        MessageDTO messageDTO=convertStringToMessageDTO(message);
        if (messageDTO != null) {
            log.info("MessageDTO: {}", messageDTO);

        } else {
            log.error("Failed to convert message to MessageDTO");
        }
        sUserServ.sendEmail(messageDTO);
    }

    //
    public MessageDTO convertStringToMessageDTO(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Register module for Java 8 date/time types
        try {
            return objectMapper.readValue(message, MessageDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}


