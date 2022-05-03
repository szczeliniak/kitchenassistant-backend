package pl.szczeliniak.kitchenassistant.receipt

import java.time.LocalDateTime

data class Photo(
    private var id_: Int = 0,
    private var fileId_: Int,
    private val createdAt_: LocalDateTime = LocalDateTime.now(),
    private var modifiedAt_: LocalDateTime = LocalDateTime.now()
) {
    val id: Int get() = id_
    val fileId: Int get() = fileId_
    val createdAt: LocalDateTime get() = createdAt_
    val modifiedAt: LocalDateTime get() = modifiedAt_

}