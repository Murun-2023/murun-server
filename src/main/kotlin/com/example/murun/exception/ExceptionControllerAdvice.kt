package com.example.murun.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler(CustomException::class)
    fun handleIllegalStateException(e: CustomException): ResponseEntity<ErrorResponse> {
        println("안녕${e.error}")
        val errorResponse = ErrorResponse(e.error)
        println(errorResponse)
        return ResponseEntity
                .status(e.error.status.value())
                .body(ErrorResponse(e.error))
    }
}