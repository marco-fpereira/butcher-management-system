package com.each.adsc.reactivebutcher.repository

import com.each.adsc.reactivebutcher.model.Purchase
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.enhanced.dynamodb.TableSchema

@Repository
class PurchaseRepository(
    private val client: DynamoDbEnhancedAsyncClient,
    @Value("\${aws.dynamodb.table.purchase}") private val tableName: String
) {

    private val table = client.table(tableName, TableSchema.fromBean(Purchase::class.java))


    fun save(purchase: Purchase): Mono<ResponseEntity<Void>> {
        return Mono.fromFuture(table.putItem(purchase)).map { ResponseEntity(HttpStatus.CREATED) }
    }
}