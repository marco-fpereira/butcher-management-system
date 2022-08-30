package com.each.adsc.blockingbutcher.exception

import com.each.adsc.blockingbutcher.model.dto.error.ErrorResponseDTO

class ValueNotFoundException(val ex: ErrorResponseDTO) : Exception()