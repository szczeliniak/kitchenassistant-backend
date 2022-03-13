package pl.szczeliniak.kitchenassistant.user

import java.time.LocalDateTime

data class User(
    private val id_: Int = 0,
    private val email_: String,
    private val password_: String,
    private val name_: String,
    private val createdAt_: LocalDateTime = LocalDateTime.now(),
    private val modifiedAt_: LocalDateTime = LocalDateTime.now()
) {
    val id: Int get() = id_
    val email: String get() = email_
    val password: String get() = password_
    val name: String get() = name_
    val createdAt: LocalDateTime get() = createdAt_
    val modifiedAt: LocalDateTime get() = modifiedAt_
}