package com.each.adsc.reactivebutcher.controller.advice

import com.each.adsc.reactivebutcher.exception.PayloadBadRequestException
import com.each.adsc.reactivebutcher.exception.PreconditionRequiredException
import com.each.adsc.reactivebutcher.exception.ValueNotFoundException
import com.each.adsc.reactivebutcher.model.dto.error.ErrorResponseDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.validation.ValidationException

@RestControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(ValueNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun valueNotFoundHandler(ex: ValueNotFoundException) : ResponseEntity<ErrorResponseDTO>{
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.errorResponseDTO)
    }

    @ExceptionHandler(PayloadBadRequestException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun payloadBadRequestException(ex: PayloadBadRequestException) : ResponseEntity<MutableList<ErrorResponseDTO>>{
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.errorResponseList)
    }

    @ExceptionHandler(PreconditionRequiredException::class)
    @ResponseStatus(HttpStatus.PRECONDITION_REQUIRED)
    fun preconditionRequiredException(ex: PreconditionRequiredException) : ResponseEntity<ErrorResponseDTO> {
        return ResponseEntity.status(HttpStatus.PRECONDITION_REQUIRED).body(ErrorResponseDTO(message = ex.message))
    }


    @ExceptionHandler(ValidationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun validationException(ex: ValidationException) : ResponseEntity<ErrorResponseDTO> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponseDTO(message = "ex.message"))
    }
}