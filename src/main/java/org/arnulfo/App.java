package org.arnulfo;

import java.util.List;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;

public class App 
{
    public static void main( String[] args )
    {
        SqsClient sqsClient = SqsClient.builder()
        .region(Region.US_EAST_1)
        .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
        .build();

        String queueUrl = "https://sqs.us-east-1.amazonaws.com/642157181326/sqs-edsalerts-main-prod-usersqs-0cb777f8-f9e5-4d53-a431-e0d9fcb16b8f";

        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
        .queueUrl(queueUrl)
        .maxNumberOfMessages(1)
        .build();
       
        List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();

        System.out.println(messages);
    }
}
