package com.splitwise.app.suser.cloud;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;


@Component
public  class EmailSender {



    public  MailjetResponse sendEmailForOrderConfirmations(String toEmailId, String fromEmailId, String receiptName, String senderName, String subject, String content ) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            return sendEmail(toEmailId, fromEmailId, receiptName, senderName, subject, content );
        } catch (MailjetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MailjetSocketTimeoutException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public  MailjetResponse sendEmail(String toEmailId, String fromEmailId, String receiptName, String senderName, String subject, String content ) throws MailjetException, MailjetSocketTimeoutException {
        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;
        System.out.println("From emailId is-->"+fromEmailId);
        System.out.println("To emailId is-->"+toEmailId);

        client = new MailjetClient("*************************",
                "*************************", new ClientOptions("v3.1"));
        request = new MailjetRequest(Emailv31.resource).property(Emailv31.MESSAGES,
                new JSONArray().put(new JSONObject()
                        .put(Emailv31.Message.FROM, new JSONObject().put("Email", fromEmailId)
                                .put(receiptName, subject))
                        .put(Emailv31.Message.TO,
                                new JSONArray().put(
                                        new JSONObject().put("Email", toEmailId).put(receiptName, "Hello")))
                        .put(Emailv31.Message.SUBJECT, subject)
                        .put(Emailv31.Message.TEXTPART, content)
                        .put(Emailv31.Message.HTMLPART,
                                "<h3>"+content)
                        .put(Emailv31.Message.CUSTOMID, "AppGettingStartedTest")));
        response = client.post(request);
        System.out.println(response.getStatus());
        System.out.println(response.getData());
        return response;
    }

}