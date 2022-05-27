package pl.szczeliniak.kitchenassistant.user.commands.factories

import java.time.ZonedDateTime

interface TokenFactory {

    fun create(userId: Int): Token

    class Token(val token: String, val validTo: ZonedDateTime)

}