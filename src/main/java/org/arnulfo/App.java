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
        // Region : US_EAST_1 based on the  "https://sqs.us-east-1.amazonaws.com/"
        // Use of EnvironmentVariableCredentialsProvider :
        // an AwsCredentialsProvider implementation that loads credentials
        // from the AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY and AWS_SESSION_TOKEN environment variables.
        // Need to make :
        // export AWS_ACCESS_KEY_ID="XXXXXXXXXXXXXXX"
        // export AWS_SECRET_ACCESS_KEY="YYYYYYY"
        // export AWS_SESSION_TOKEN="WWWWWWWWW"
        // AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY, AWS_SESSION_TOKEN : from https://api.refinitiv.com/auth/cloud-credentials/v1/?endpoint= 
        
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
