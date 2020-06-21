package ru.service.test.payment.common.handlers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception): ResponseEntity<Any?>? = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(getExceptionData(exception))

    private fun getExceptionData(exception: Throwable): ErrorDescription? = ErrorDescription(exception.message)

    class ErrorDescription() {

        var rootCauseMessage: String? = null

        constructor(rootCauseMessage: String?): this() {
            this.rootCauseMessage = rootCauseMessage
        }

    }

}
