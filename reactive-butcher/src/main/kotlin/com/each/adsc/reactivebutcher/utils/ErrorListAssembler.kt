package com.each.adsc.reactivebutcher.utils

import com.each.adsc.reactivebutcher.model.dto.error.ErrorResponseDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult

class ErrorListAssembler {
    companion object {
        fun generateErrorList(result: BindingResult) : ResponseEntity<Any> {
            val fieldErrorList: MutableList<ErrorResponseDTO> = mutableListOf()
            result.fieldErrors.forEach {
                fieldErrorList.add(ErrorResponseDTO(field = it.field, message = it.defaultMessage!!))
            }
            return ResponseEntity(fieldErrorList, HttpStatus.BAD_REQUEST)
        }
    }
}