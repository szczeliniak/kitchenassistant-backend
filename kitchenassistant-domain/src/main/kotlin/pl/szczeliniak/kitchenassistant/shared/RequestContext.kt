package pl.szczeliniak.kitchenassistant.shared

import java.util.*

interface RequestContext {

    fun userId(userId: Int?)
    fun userId(): Int?
    fun requestId(requestId: UUID?)
    fun requestId(): UUID?
    fun requireUserId(): Int

}