package com.each.adsc.reactivebutcher.exception

import com.each.adsc.reactivebutcher.model.dto.error.ErrorResponseDTO

class PayloadBadRequestException(
    override val message: String? = null,
    e: Throwable? = null,
    val errorResponseList: MutableList<ErrorResponseDTO>
) : Exception(message, e)