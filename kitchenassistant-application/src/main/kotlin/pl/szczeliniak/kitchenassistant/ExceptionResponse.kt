package pl.szczeliniak.kitchenassistant

import java.time.ZonedDateTime

data class ExceptionResponse(
    val message: String?,
    val timestamp: ZonedDateTime = ZonedDateTime.now()
)
