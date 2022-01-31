package com.example.restfulwebservice.exception

import com.example.restfulwebservice.user.UserNotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*

@RestController
@ControllerAdvice
class CustomizedResponseEntityExceptionHandler: ResponseEntityExceptionHandler() {
    @ExceptionHandler(Exception::class)
    final fun handleAllExceptions(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val exceptionResponse = ex.message?.let { ExceptionResponse(Date(), it, request.getDescription(false)) }
        return ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(UserNotFoundException::class)
    final fun handUserNotFoundException(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val exceptionResponse = ex.message?.let { ExceptionResponse(Date(), it, request.getDescription(false)) }
        return ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val exceptionResponse = ExceptionResponse(Date(), "Validation failed!", ex.bindingResult.toString())
        return ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST)
    }
}