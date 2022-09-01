package com.each.adsc.reactivebutcher.exception

import com.each.adsc.reactivebutcher.model.dto.error.ErrorResponseDTO

class ValueNotFoundException(
    override val message: String? = null,
    e: Throwable? = null,
    val errorResponseDTO: ErrorResponseDTO
) : RuntimeException(message, e)