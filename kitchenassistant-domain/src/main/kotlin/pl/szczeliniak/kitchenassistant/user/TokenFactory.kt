package pl.szczeliniak.kitchenassistant.user

import java.time.ZonedDateTime

interface TokenFactory {

    fun create(userId: Int, email: String): Token

    class Token(val token: String, val email: String, val validTo: ZonedDateTime)

}