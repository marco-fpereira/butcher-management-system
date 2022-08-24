package com.each.adsc.blockingbutcher.configuration

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableDynamoDBRepositories(basePackages = ["com.each.adsc.blockingbutcher.repository"])
class DynamoDBConfig {

    @Value("\${aws.dynamodb.endpoint}")
    private lateinit var dynamodbEndpoint: String

    @Value("\${aws.dynamodb.region}")
    private lateinit var awsRegion: String

    @Value("\${aws.dynamodb.accesskey}")
    private lateinit var dynamodbAccessKey: String

    @Value("\${aws.dynamodb.secretkey}")
    private lateinit var dynamodbSecretKey: String

    @Bean(name = ["amazonDynamoDB"])
    fun getDynamoDBClient(): AmazonDynamoDB {
        return AmazonDynamoDBClient
            .builder()
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(dynamodbEndpoint, awsRegion))
            .withCredentials(
                AWSStaticCredentialsProvider(
                    BasicAWSCredentials(dynamodbAccessKey, dynamodbSecretKey)
                )
            )
            .build()
    }

}