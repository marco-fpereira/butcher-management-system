package com.each.adsc.blockingbutcher.model.dto

import com.each.adsc.blockingbutcher.model.TypeOfCut
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.PositiveOrZero

data class SaleDTO(
    @JsonProperty("sale_id")
    val saleId: String? = null,

    @JsonProperty("amount")
    @field:PositiveOrZero
    val amount: Double,

    @JsonProperty("total_price")
    @field:PositiveOrZero
    val totalPrice: Double,

    @JsonProperty("type_of_cut")
    @field:NotBlank
    val typeOfCut: TypeOfCut,

    @JsonProperty("timestamp")
    val saleTimestamp: LocalDateTime
)
