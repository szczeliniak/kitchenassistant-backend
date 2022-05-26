package pl.szczeliniak.kitchenassistant.user.commands.factories

import java.time.LocalDateTime

interface TokenFactory {

    fun create(userId: Int): Token

    class Token(val token: String, val validTo: LocalDateTime)

}