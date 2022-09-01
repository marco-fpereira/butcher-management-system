package com.each.adsc.blockingbutcher.exception

import com.each.adsc.blockingbutcher.model.dto.error.ErrorResponseDTO

class ValueNotFoundException(
    override val message: String? = null,
    e: Throwable? = null,
    val errorResponseDTO: ErrorResponseDTO
) : RuntimeException(message, e)