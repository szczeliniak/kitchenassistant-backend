package pl.szczeliniak.kitchenassistant.user

import pl.szczeliniak.kitchenassistant.shared.TokenType

interface TokenFactory {

    fun create(userId: Int, email: String, tokenType: TokenType): String

}