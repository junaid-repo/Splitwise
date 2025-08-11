package com.splitwise.app.suser.config;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import io.awspring.cloud.sqs.support.converter.SqsMessagingMessageConverter;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
public class AwsSQSConfig {

	//@Value("${spring.cloud.aws.region.static}")
	private String region="eu-north-1";

	//@Value("${spring.cloud.aws.credentials.access-key}")
	private String awsAccessKey="*************************";

	//@Value("${spring.cloud.aws.credentials.secret-key}")
	private String awsSecretKey="*************************";


    @Bean
    public SqsAsyncClient sqsClient() {
        return SqsAsyncClient.builder()
                .region(Region.of(region)) // Use your desired region
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(awsAccessKey, awsSecretKey)
                        )
                )
                .build();
    }

    @Bean
    public SqsTemplate sqsTemplate(SqsAsyncClient sqsClient) {
        return SqsTemplate.builder()
                .sqsAsyncClient(sqsClient)
                .messageConverter(new SqsMessagingMessageConverter())
                .build();
    }

	@PostConstruct
	public void init() {
		System.out.println("The credentials are "+region +"  "+awsAccessKey+"  "+awsSecretKey);
		System.out.println(
                StaticCredentialsProvider.create(
	                    AwsBasicCredentials.create("awsAccessKey", "awsSecretKey")
	                )
	            );

	}
	
	
}
