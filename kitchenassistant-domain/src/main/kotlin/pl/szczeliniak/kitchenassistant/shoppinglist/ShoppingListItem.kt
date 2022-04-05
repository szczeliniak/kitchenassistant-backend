package pl.szczeliniak.kitchenassistant.shoppinglist

import pl.szczeliniak.kitchenassistant.exceptions.NotAllowedOperationException
import java.time.LocalDateTime

data class ShoppingListItem(
    private var id_: Int = 0,
    private var name_: String,
    private var quantity_: String,
    private var sequence_: Int? = null,
    private var deleted_: Boolean = false,
    private var done_: Boolean = false,
    private val createdAt_: LocalDateTime = LocalDateTime.now(),
    private var modifiedAt_: LocalDateTime = LocalDateTime.now()
) {
    val id: Int get() = id_
    val name: String get() = name_
    val quantity: String get() = quantity_
    val sequence: Int? get() = sequence_
    val createdAt: LocalDateTime get() = createdAt_
    val modifiedAt: LocalDateTime get() = modifiedAt_
    val done: Boolean get() = done_
    val deleted: Boolean get() = deleted_

    fun markAsDeleted() {
        if (deleted_) {
            throw NotAllowedOperationException("Shopping list item is already marked as deleted!")
        }
        deleted_ = true
        this.modifiedAt_ = LocalDateTime.now()
    }

    fun markAsDone(done: Boolean) {
        done_ = done
        this.modifiedAt_ = LocalDateTime.now()
    }

    fun update(name: String, quantity: String, sequence: Int?) {
        this.name_ = name
        this.quantity_ = quantity
        this.sequence_ = sequence
        this.modifiedAt_ = LocalDateTime.now()
    }

}