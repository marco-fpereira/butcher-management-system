package com.each.adsc.blockingbutcher.model

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import java.time.LocalDateTime

@DynamoDBTable(tableName = "sale_tb")
data class Sale(
    @DynamoDBHashKey(attributeName = "sale_id")
    @DynamoDBAutoGeneratedKey
    val saleId: String? = null,

    @DynamoDBAttribute(attributeName = "amount")
    val amount: Double,

    @DynamoDBAttribute(attributeName = "total_price")
    val totalPrice: Double,

    @DynamoDBAttribute(attributeName = "type_of_cut")
    val typeOfCut: TypeOfCut,

    @DynamoDBAttribute(attributeName = "timestamp")
    val saleTimestamp: LocalDateTime
)

enum class TypeOfCut {
    GROUND,
    MINCED,
    CUBED,
    FILLET,
}
