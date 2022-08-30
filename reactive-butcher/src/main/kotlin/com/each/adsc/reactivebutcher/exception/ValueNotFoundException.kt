package com.each.adsc.reactivebutcher.exception

import com.each.adsc.reactivebutcher.model.dto.error.ErrorResponseDTO

class ValueNotFoundException(ex: ErrorResponseDTO) : Exception()