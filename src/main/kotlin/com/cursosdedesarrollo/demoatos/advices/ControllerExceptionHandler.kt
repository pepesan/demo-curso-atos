package com.example.demogradlekotlin.advices

import com.example.demogradlekotlin.dto.ErrorMessage
import lombok.extern.slf4j.Slf4j
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.*

@ControllerAdvice
class ResourceNotFoundExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleValidationException(e: ResourceNotFoundException): ResponseEntity<ErrorMessage> {
        val message: ErrorMessage = ErrorMessage(
            404,
            Date(),
            e.message,
            "Error capturado por ResourceNotFoundException"
        )
        return ResponseEntity<ErrorMessage>(
            message,
            HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleResourceNotFound(ex: MethodArgumentNotValidException)
    : ResponseEntity<ErrorMessage> {
        // Obtén los mensajes de error de la excepción
        val errorMessage = ex.bindingResult.allErrors[0].defaultMessage

        // Puedes personalizar la respuesta según tus necesidades
        val apiError: ErrorMessage = ErrorMessage(
            400,
            Date(),
            errorMessage,
            "Error capturado por MethodArgumentNotValidException"
        )
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        // Devuelve la respuesta con el código de estado adecuado
        return ResponseEntity<ErrorMessage>(apiError, headers, HttpStatus.BAD_REQUEST)
    }
}
