package pl.szczeliniak.kitchenassistant

import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import java.time.ZonedDateTime

data class ExceptionResponse(
    val errorCode: ErrorCode,
    val message: String?,
    val timestamp: ZonedDateTime = ZonedDateTime.now()
)
