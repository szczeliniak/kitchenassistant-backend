package pl.szczeliniak.kitchenassistant.shared

import java.util.*

interface RequestContext {

    fun userId(userId: Int?)
    fun requestId(requestId: UUID?)
    fun requestId(): UUID?
    fun userId(): Int
    fun tokenType(tokenType: TokenType)
    fun tokenType(): TokenType

}