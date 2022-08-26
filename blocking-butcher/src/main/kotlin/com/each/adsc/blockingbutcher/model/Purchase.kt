package com.each.adsc.blockingbutcher.model

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import java.io.Serializable
import java.time.LocalDateTime

@DynamoDBTable(tableName = "purchase_tb")
data class Purchase(
    @DynamoDBHashKey(attributeName = "purchase_id")
    @DynamoDBAutoGeneratedKey
    var purchaseId: String? = null,

    @DynamoDBAttribute(attributeName = "meat_name")
    val meatName: String = "",

    @DynamoDBAttribute(attributeName = "animal_of_origin")
    val animalOfOrigin: String = "",

    @DynamoDBAttribute(attributeName = "purchase_price")
    val purchasePriceInKilograms: Double = 0.0,

    @DynamoDBAttribute(attributeName = "amount_bought")
    val amountBought: Double = 0.0,

    @DynamoDBAttribute(attributeName = "timestamp")
    val purchaseTimestamp: String = LocalDateTime.now().toString()
) : Serializable
