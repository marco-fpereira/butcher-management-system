package com.each.adsc.reactivebutcher.model

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import java.io.Serializable

@DynamoDbBean
data class Meat(
    @get:DynamoDbPartitionKey
    @get:DynamoDbAttribute("meat_name")
    var meatName: String = "",

    @get:DynamoDbAttribute("animal_of_origin")
    var animalOfOrigin: String = "",

    @get:DynamoDbAttribute("price")
    var price: Double = 0.0,

    @get:DynamoDbAttribute("available_amount")
    var availableAmountInKilograms: Double = 0.0
) : Serializable
