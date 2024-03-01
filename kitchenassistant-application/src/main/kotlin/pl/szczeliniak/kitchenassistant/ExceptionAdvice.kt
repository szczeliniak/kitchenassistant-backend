package pl.szczeliniak.kitchenassistant

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException

@RestControllerAdvice
class ExceptionAdvice : ResponseEntityExceptionHandler() {

    @ExceptionHandler(KitchenAssistantException::class)
    fun exception(kitchenAssistantException: KitchenAssistantException): ResponseEntity<Any> {
        return error(kitchenAssistantException.error, kitchenAssistantException)
    }

    @ExceptionHandler(Exception::class)
    fun exception(exception: Exception): ResponseEntity<Any> {
        return error(ErrorCode.GENERIC_INTERNAL_ERROR, exception)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        return error(ErrorCode.GENERIC_INTERNAL_ERROR, ex)
    }

    private fun error(
        errorCode: ErrorCode,
        exception: Exception
    ): ResponseEntity<Any> {
        logger.error(exception.message, exception)
        return ResponseEntity<Any>(ExceptionResponse(errorCode, errorCode.message), HttpStatus.valueOf(errorCode.code))
    }

}