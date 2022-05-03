package pl.szczeliniak.kitchenassistant.receipt

import java.time.LocalDateTime

data class Tag(
    private var id_: Int = 0,
    private var name_: String,
    private var userId_: Int,
    private val createdAt_: LocalDateTime = LocalDateTime.now(),
    private var modifiedAt_: LocalDateTime = LocalDateTime.now()
) {
    val id: Int get() = id_
    val name: String get() = name_
    val userId: Int get() = userId_
    val createdAt: LocalDateTime get() = createdAt_
    val modifiedAt: LocalDateTime get() = modifiedAt_

    fun update(name: String) {
        this.name_ = name
        this.modifiedAt_ = LocalDateTime.now()
    }

}