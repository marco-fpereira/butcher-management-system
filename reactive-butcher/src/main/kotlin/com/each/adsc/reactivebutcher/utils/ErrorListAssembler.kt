package com.each.adsc.reactivebutcher.utils

import com.each.adsc.reactivebutcher.exception.PayloadBadRequestException
import com.each.adsc.reactivebutcher.model.dto.error.ErrorResponseDTO
import org.springframework.validation.BindingResult
import reactor.core.publisher.Mono

class ErrorListAssembler {
    companion object {
        fun generateErrorList(result: BindingResult) : Mono<Any> {
            val fieldErrorList: MutableList<ErrorResponseDTO> = mutableListOf()
            result.fieldErrors.forEach {
                fieldErrorList.add(ErrorResponseDTO(field = it.field, message = it.defaultMessage!!))
            }
            return Mono.error{ PayloadBadRequestException(errorResponseList = fieldErrorList) }
        }
    }
}