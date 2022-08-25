package com.each.adsc.blockingbutcher.model.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.PositiveOrZero

data class MeatDTO(
    @JsonProperty("meat_id")
    val meatId: String? = null,

    @JsonProperty("name")
    @field:NotBlank
    val name: String,

    @JsonProperty("animal_of_origin")
    @field:NotBlank
    val animalOfOrigin: String,

    @JsonProperty("price")
    @field:PositiveOrZero
    val price: Double,

    @JsonProperty("available_amount_in_kilograms")
    @field:PositiveOrZero
    val availableAmountInKilograms: Double
)
