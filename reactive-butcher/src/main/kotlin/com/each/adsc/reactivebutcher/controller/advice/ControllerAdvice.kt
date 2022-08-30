package com.each.adsc.reactivebutcher.controller.advice

import com.each.adsc.reactivebutcher.exception.ValueNotFoundException
import com.each.adsc.reactivebutcher.model.dto.error.ErrorResponseDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(ValueNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun valueNotFoundHandler(ex: ErrorResponseDTO) : ValueNotFoundException{
        return ValueNotFoundException(ex)
    }
}