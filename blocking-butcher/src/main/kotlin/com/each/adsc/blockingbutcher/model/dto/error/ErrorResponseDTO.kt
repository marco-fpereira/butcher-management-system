package com.each.adsc.blockingbutcher.model.dto.error

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorResponseDTO(
    val field : String? = null,
    val message: String? = null
)
