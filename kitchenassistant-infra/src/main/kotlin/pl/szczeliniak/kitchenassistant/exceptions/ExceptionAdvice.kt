package pl.szczeliniak.kitchenassistant.exceptions

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionAdvice {

    private val logger = LoggerFactory.getLogger(ExceptionAdvice::class.java)

    @ExceptionHandler(Exception::class)
    fun exception(exception: Exception): ResponseEntity<ExceptionResponse> {
        return error(HttpStatus.BAD_REQUEST, exception)
    }

    @ExceptionHandler(NotFoundException::class)
    fun notFoundException(exception: NotFoundException): ResponseEntity<ExceptionResponse> {
        return error(HttpStatus.NOT_FOUND, exception)
    }

    @ExceptionHandler(NotAllowedOperationException::class)
    fun notFoundException(exception: NotAllowedOperationException): ResponseEntity<ExceptionResponse> {
        return error(HttpStatus.BAD_REQUEST, exception)
    }

    private fun error(
        httpStatus: HttpStatus,
        exception: Exception
    ): ResponseEntity<ExceptionResponse> {
        logger.error(exception.message, exception)
        return ResponseEntity<ExceptionResponse>(
            ExceptionResponse(exception.message), httpStatus
        )
    }

}