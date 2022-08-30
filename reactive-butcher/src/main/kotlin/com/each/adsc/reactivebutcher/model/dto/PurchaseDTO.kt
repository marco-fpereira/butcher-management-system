package com.each.adsc.reactivebutcher.model.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.PositiveOrZero

data class PurchaseDTO(
    @JsonProperty("purchase_id")
    var purchaseId: String? = null,

    @JsonProperty("meat_name")
    @field:NotBlank
    val meatName: String,

    @JsonProperty("animal_of_origin")
    @field:NotBlank
    val animalOfOrigin: String,

    @JsonProperty("purchase_price")
    @field:PositiveOrZero
    val purchasePriceInKilograms: Double,

    @JsonProperty("amount_bought")
    @field:PositiveOrZero
    val amountBought: Double,

    @JsonProperty("timestamp")
    val purchaseTimestamp: LocalDateTime = LocalDateTime.now()
)
