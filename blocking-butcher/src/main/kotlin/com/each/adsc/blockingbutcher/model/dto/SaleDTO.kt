package com.each.adsc.blockingbutcher.model.dto

import com.each.adsc.blockingbutcher.model.dto.TypeOfCut
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.PositiveOrZero

data class SaleDTO(
    @JsonProperty("sale_id")
    var saleId: String? = null,

    @JsonProperty("meat_name")
    @field:NotBlank
    val meatName: String,

    @JsonProperty("amount_in_kilograms")
    @field:PositiveOrZero
    val amount: Double,

    @JsonProperty("total_price")
    @field:PositiveOrZero
    var totalPrice: Double = 0.0,

    @JsonProperty("type_of_cut")
    @field:NotBlank
    val typeOfCut: TypeOfCut,

    @JsonProperty("timestamp")
    val saleTimestamp: LocalDateTime = LocalDateTime.now()
)

enum class TypeOfCut {
    GROUND,
    MINCED,
    CUBED,
    FILLET,
}
