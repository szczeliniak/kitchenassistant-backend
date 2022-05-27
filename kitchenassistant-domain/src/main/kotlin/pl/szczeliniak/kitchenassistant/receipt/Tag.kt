package pl.szczeliniak.kitchenassistant.receipt

import java.time.ZonedDateTime

data class Tag(
    private var id_: Int = 0,
    private var name_: String,
    private var userId_: Int,
    private val createdAt_: ZonedDateTime = ZonedDateTime.now(),
    private var modifiedAt_: ZonedDateTime = ZonedDateTime.now()
) {
    val id: Int get() = id_
    val name: String get() = name_
    val userId: Int get() = userId_
    val createdAt: ZonedDateTime get() = createdAt_
    val modifiedAt: ZonedDateTime get() = modifiedAt_

    fun update(name: String) {
        this.name_ = name
        this.modifiedAt_ = ZonedDateTime.now()
    }

}