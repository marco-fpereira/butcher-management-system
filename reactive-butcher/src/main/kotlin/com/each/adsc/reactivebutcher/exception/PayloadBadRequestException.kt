package com.each.adsc.reactivebutcher.exception

import com.each.adsc.reactivebutcher.model.dto.error.ErrorResponseDTO

class PayloadBadRequestException(ex: MutableList<ErrorResponseDTO>) : Exception()