package pl.szczeliniak.kitchenassistant

import java.time.LocalDateTime

data class ExceptionResponse(
    val message: String?,
    val timestamp: LocalDateTime = LocalDateTime.now()
)
