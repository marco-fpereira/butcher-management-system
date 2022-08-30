package com.each.adsc.reactivebutcher.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import java.net.URI

@Configuration
@ComponentScan("com.each.adsc.reactivebutcher.repository")
class DynamoDBConfig {

    @Value("\${aws.dynamodb.endpoint}")
    private lateinit var dynamodbEndpoint: String

    @Value("\${aws.dynamodb.region}")
    private lateinit var awsRegion: String

    @Value("\${aws.dynamodb.accesskey}")
    private lateinit var dynamodbAccessKey: String

    @Value("\${aws.dynamodb.secretkey}")
    private lateinit var dynamodbSecretKey: String

    @Bean
    fun dynamoDbAsyncClient(): DynamoDbAsyncClient {
        return DynamoDbAsyncClient
            .builder()
            .region(Region.of(awsRegion))
            .endpointOverride(URI.create(dynamodbEndpoint))
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(dynamodbAccessKey, dynamodbSecretKey)
                )
            )
            .build()
    }

    @Bean
    fun dynamoDbEnhancedAsyncClient(): DynamoDbEnhancedAsyncClient {
        return DynamoDbEnhancedAsyncClient.builder()
            .dynamoDbClient(dynamoDbAsyncClient())
            .build()
    }

}