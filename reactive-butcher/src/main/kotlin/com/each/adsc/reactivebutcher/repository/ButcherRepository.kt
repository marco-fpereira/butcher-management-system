package com.each.adsc.reactivebutcher.repository

import com.each.adsc.reactivebutcher.model.Meat
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema

@Repository
class ButcherRepository (
    private val client: DynamoDbEnhancedAsyncClient,
    @Value("\${aws.dynamodb.table.butcher}") private val tableName: String
){

    private val table = client.table(tableName, TableSchema.fromBean(Meat::class.java))

    fun findById(meatId: String): Mono<Meat> {
        return Mono.fromFuture(table.getItem(getKeyBuild(meatId)))
    }

    fun findByIdWithDefaultValue(meatId: String, defaultIfEmpty: Meat): Mono<Meat> {
        return Mono.fromFuture(table.getItem(getKeyBuild(meatId)))
            .defaultIfEmpty(defaultIfEmpty)
    }

    fun save(meat: Meat): Mono<ResponseEntity<Void>> {
        return Mono.fromFuture(table.putItem(meat)).map { ResponseEntity(HttpStatus.CREATED) }
    }

    fun update(meat: Meat): Mono<ResponseEntity<Void>> {
        return Mono.fromFuture(table.updateItem(meat)).map { ResponseEntity(HttpStatus.CREATED) }
    }

    fun findAll(): Flux<Meat> {
        return table.scan().items().toFlux()
    }

    fun getKeyBuild(id: String) : Key = Key.builder().partitionValue(id).build()

}