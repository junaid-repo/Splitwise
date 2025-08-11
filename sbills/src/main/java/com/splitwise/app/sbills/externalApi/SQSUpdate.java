package com.splitwise.app.sbills.externalApi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.awspring.cloud.sqs.operations.SqsTemplate;

@Service
public class SQSUpdate {

	private final SqsTemplate sqsTemplate;

	@Value("${email.queueName}")
	private String queueName;

	public SQSUpdate(SqsTemplate sqsTemplate) {
		this.sqsTemplate = sqsTemplate;
	}

	public void sendMessage(String message) {
		System.out.println("Message sent to SQS: " + message);
		sqsTemplate.send(queueName, message);
	}
}
