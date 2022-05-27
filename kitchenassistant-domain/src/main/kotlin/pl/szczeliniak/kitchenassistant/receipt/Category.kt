package pl.szczeliniak.kitchenassistant.receipt

import pl.szczeliniak.kitchenassistant.shared.exceptions.NotAllowedOperationException
import java.time.ZonedDateTime

data class Category(
    private var id_: Int = 0,
    private var name_: String,
    private var userId_: Int,
    private var deleted_: Boolean = false,
    private val createdAt_: ZonedDateTime = ZonedDateTime.now(),
    private var modifiedAt_: ZonedDateTime = ZonedDateTime.now()
) {
    val id: Int get() = id_
    val name: String get() = name_
    val userId: Int get() = userId_
    val deleted: Boolean get() = deleted_
    val createdAt: ZonedDateTime get() = createdAt_
    val modifiedAt: ZonedDateTime get() = modifiedAt_

    fun markAsDeleted() {
        if (deleted) {
            throw NotAllowedOperationException("Category is already marked as deleted!")
        }
        deleted_ = true
        this.modifiedAt_ = ZonedDateTime.now()
    }

    fun update(name: String) {
        this.name_ = name
        this.modifiedAt_ = ZonedDateTime.now()
    }

}