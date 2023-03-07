package pl.szczeliniak.kitchenassistant

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException

@RestControllerAdvice
class ExceptionAdvice : ResponseEntityExceptionHandler() {

    @ExceptionHandler(KitchenAssistantException::class)
    fun exception(kitchenAssistantException: KitchenAssistantException): ResponseEntity<Any> {
        return error(HttpStatus.valueOf(kitchenAssistantException.error.code), kitchenAssistantException)
    }

    @ExceptionHandler(Exception::class)
    fun exception(exception: Exception): ResponseEntity<Any> {
        return error(HttpStatus.BAD_REQUEST, exception)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        return error(HttpStatus.BAD_REQUEST, ex)
    }

    private fun error(
        httpStatus: HttpStatus,
        exception: Exception
    ): ResponseEntity<Any> {
        logger.error(exception.message, exception)
        return ResponseEntity<Any>(ExceptionResponse(exception.message), httpStatus)
    }

}