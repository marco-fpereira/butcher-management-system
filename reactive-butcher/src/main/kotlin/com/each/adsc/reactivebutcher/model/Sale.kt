package com.each.adsc.reactivebutcher.model

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import java.io.Serializable
import java.time.LocalDateTime

@DynamoDbBean
data class Sale(
    @get:DynamoDbPartitionKey
    @get:DynamoDbAttribute("sale_id")
    var saleId: String? = null,

    @get:DynamoDbAttribute("meat_name")
    var meatName: String = "",

    @get:DynamoDbAttribute("amount")
    val amount: Double = 0.0,

    @get:DynamoDbAttribute("total_price")
    var totalPrice: Double = 0.0,

    @get:DynamoDbAttribute("type_of_cut")
    val typeOfCut: String = "",

    @get:DynamoDbAttribute("timestamp")
    var saleTimestamp: String = LocalDateTime.now().toString()
) : Serializable

