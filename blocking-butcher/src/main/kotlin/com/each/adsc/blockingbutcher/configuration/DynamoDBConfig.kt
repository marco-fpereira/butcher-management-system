package com.each.adsc.blockingbutcher.configuration

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.URI

@Configuration
class DynamoDBConfig {

    @Value("\${aws.dynamodb.endpoint}")
    private lateinit var dynamodbEndpoint: String

    @Value("\${aws.dynamodb.region}")
    private lateinit var awsRegion: String

    @Value("\${aws.dynamo.accesskey}")
    private lateinit var dynamodbAccessKey: String

    @Value("\${aws.dynamo.secretkey}")
    private lateinit var dynamodbSecretKey: String

    @Bean
    fun getDynamoDBClient(): AmazonDynamoDB {
        return AmazonDynamoDBClient
            .builder()
            .withRegion(Regions.fromName(awsRegion))
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(dynamodbEndpoint, awsRegion))
            .withCredentials(
                AWSStaticCredentialsProvider(
                    BasicAWSCredentials(dynamodbAccessKey, dynamodbSecretKey)
                )
            )
            .build()
    }

}