package it.polito.group24.lab3.controllers

import it.polito.group24.lab3.exceptions.DuplicateEntryException
import it.polito.group24.lab3.exceptions.UnmatchedActivationCodeException
import it.polito.group24.lab3.exceptions.WrongActivationIDException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.sql.SQLException

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(
        DuplicateEntryException::class,
        UnmatchedActivationCodeException::class,
        WrongActivationIDException::class
    )
    protected fun handleMinorException(e: Exception): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }
}