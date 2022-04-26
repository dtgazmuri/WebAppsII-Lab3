package it.group24.lab3.controllers

import it.group24.lab3.CustomExceptions.*
import it.group24.lab3.dtos.ErrorDetails
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.*

@ControllerAdvice
class ExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongFieldException::class)
    fun wrongEmail(e: WrongFieldException): ErrorDetails{
        val messages = e.message?.split("---")?.filter{ x -> x.isNotEmpty() }?.toList()!!
        return ErrorDetails(Date(), messages)
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsernameAlreadyRegisteredException::class)
    fun wrongUsername(e: UsernameAlreadyRegisteredException): ErrorDetails{
        var messages = mutableListOf<String>(e.message!!)
        return ErrorDetails(Date(), messages)
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(EmailNotSentException::class)
    fun wrongUsername(e: EmailNotSentException): ErrorDetails{
        var messages = mutableListOf<String>(e.message!!)
        return ErrorDetails(Date(), messages)
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ActivationNotFoundException::class)
    fun activationNotFound(e: ActivationNotFoundException): ErrorDetails{
        var messages = mutableListOf<String>(e.message!!)
        return ErrorDetails(Date(), messages)
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ActivationCodeNotValidException::class)
    fun activationNotFound(e: ActivationCodeNotValidException): ErrorDetails{
        var messages = mutableListOf<String>(e.message!!)
        return ErrorDetails(Date(), messages)
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AttemptCounterException::class)
    fun activationNotFound(e: AttemptCounterException): ErrorDetails{
        var messages = mutableListOf<String>(e.message!!)
        return ErrorDetails(Date(), messages)
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailAlreadyRegisteredException::class)
    fun activationNotFound(e: EmailAlreadyRegisteredException): ErrorDetails{
        var messages = mutableListOf<String>(e.message!!)
        return ErrorDetails(Date(), messages)
    }

}