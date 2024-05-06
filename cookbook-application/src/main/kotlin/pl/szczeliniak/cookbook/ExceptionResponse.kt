package pl.szczeliniak.cookbook

import pl.szczeliniak.cookbook.shared.ErrorCode
import java.time.ZonedDateTime

data class ExceptionResponse(
    val errorCode: ErrorCode,
    val message: String?,
    val timestamp: ZonedDateTime = ZonedDateTime.now()
)
