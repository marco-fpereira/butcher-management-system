package com.each.adsc.blockingbutcher.controller.advice

import com.each.adsc.blockingbutcher.exception.ValueNotFoundException
import com.each.adsc.blockingbutcher.model.dto.error.ErrorResponseDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(ValueNotFoundException::class)
    fun valueNotFoundHandler(ex: ValueNotFoundException) : ResponseEntity<ErrorResponseDTO> {
        return ResponseEntity(ex.errorResponseDTO, HttpStatus.NOT_FOUND)
    }
}