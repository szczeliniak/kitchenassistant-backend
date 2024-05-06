package pl.szczeliniak.cookbook.user

import pl.szczeliniak.cookbook.shared.TokenType

interface TokenFactory {

    fun create(userId: Int, email: String, tokenType: TokenType): String

}