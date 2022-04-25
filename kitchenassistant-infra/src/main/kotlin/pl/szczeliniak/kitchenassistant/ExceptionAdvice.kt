package pl.szczeliniak.kitchenassistant

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import pl.szczeliniak.kitchenassistant.shared.exceptions.*

@RestControllerAdvice
class ExceptionAdvice : ResponseEntityExceptionHandler() {

    @ExceptionHandler(Exception::class)
    fun exception(exception: Exception): ResponseEntity<Any> {
        return error(HttpStatus.BAD_REQUEST, exception)
    }

    @ExceptionHandler(NotFoundException::class)
    fun notFoundException(exception: NotFoundException): ResponseEntity<Any> {
        return error(HttpStatus.NOT_FOUND, exception)
    }

    @ExceptionHandler(NotAllowedOperationException::class)
    fun notFoundException(exception: NotAllowedOperationException): ResponseEntity<Any> {
        return error(HttpStatus.BAD_REQUEST, exception)
    }

    @ExceptionHandler(UserExistsException::class)
    fun userExistsException(exception: UserExistsException): ResponseEntity<Any> {
        return error(HttpStatus.CONFLICT, exception)
    }

    @ExceptionHandler(FileTooLargeException::class)
    fun fileTooLargeException(exception: FileTooLargeException): ResponseEntity<Any> {
        return error(HttpStatus.PAYLOAD_TOO_LARGE, exception)
    }

    @ExceptionHandler(FtpException::class)
    fun ftpException(exception: FtpException): ResponseEntity<Any> {
        return error(HttpStatus.FAILED_DEPENDENCY, exception)
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