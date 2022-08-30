package com.each.adsc.reactivebutcher.model

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import java.io.Serializable
import java.time.LocalDateTime

@DynamoDbBean
data class Purchase(
    @get:DynamoDbPartitionKey
    @get:DynamoDbAttribute("purchase_id")
    var purchaseId: String? = null,

    @get:DynamoDbAttribute("meat_name")
    val meatName: String = "",

    @get:DynamoDbAttribute("animal_of_origin")
    val animalOfOrigin: String = "",

    @get:DynamoDbAttribute("purchase_price")
    val purchasePriceInKilograms: Double = 0.0,

    @get:DynamoDbAttribute("amount_bought")
    val amountBought: Double = 0.0,

    @get:DynamoDbAttribute("timestamp")
    val purchaseTimestamp: String = LocalDateTime.now().toString()
) : Serializable
