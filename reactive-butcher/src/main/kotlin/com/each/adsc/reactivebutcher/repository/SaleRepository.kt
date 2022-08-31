package com.each.adsc.reactivebutcher.repository

import com.each.adsc.reactivebutcher.model.Sale
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.enhanced.dynamodb.TableSchema

@Repository
class SaleRepository(
    private val client: DynamoDbEnhancedAsyncClient,
    @Value("\${aws.dynamodb.table.sale}") private val tableName: String
) {

    private val table = client.table(tableName, TableSchema.fromBean(Sale::class.java))

    fun save(sale: Sale): Mono<Void> {
//        val putItem: PutItemEnhancedRequest<Sale> = PutItemEnhancedRequest.builder(Sale::class.java).item(sale).build()
//        return Mono.just(table.putItemWithResponse(putItem).get().attributes())
        return Mono.fromFuture(table.putItem(sale))
    }
}