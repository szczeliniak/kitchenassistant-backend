package pl.szczeliniak.kitchenassistant.receipt

import java.time.ZonedDateTime

data class Photo(
    private var id_: Int = 0,
    private var fileId_: Int,
    private val createdAt_: ZonedDateTime = ZonedDateTime.now(),
    private var modifiedAt_: ZonedDateTime = ZonedDateTime.now()
) {
    val id: Int get() = id_
    val fileId: Int get() = fileId_
    val createdAt: ZonedDateTime get() = createdAt_
    val modifiedAt: ZonedDateTime get() = modifiedAt_

}