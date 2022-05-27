package pl.szczeliniak.kitchenassistant.shoppinglist

import pl.szczeliniak.kitchenassistant.shared.exceptions.NotAllowedOperationException
import java.time.ZonedDateTime

data class ShoppingListItem(
    private var id_: Int = 0,
    private var name_: String,
    private var quantity_: String,
    private var sequence_: Int? = null,
    private var receiptId_: Int? = null,
    private var deleted_: Boolean = false,
    private var completed_: Boolean = false,
    private val createdAt_: ZonedDateTime = ZonedDateTime.now(),
    private var modifiedAt_: ZonedDateTime = ZonedDateTime.now()
) {
    val id: Int get() = id_
    val name: String get() = name_
    val quantity: String get() = quantity_
    val sequence: Int? get() = sequence_
    val receiptId: Int? get() = receiptId_
    val createdAt: ZonedDateTime get() = createdAt_
    val modifiedAt: ZonedDateTime get() = modifiedAt_
    val completed: Boolean get() = completed_
    val deleted: Boolean get() = deleted_

    fun markAsDeleted() {
        if (deleted_) {
            throw NotAllowedOperationException("Shopping list item is already marked as deleted!")
        }
        deleted_ = true
        this.modifiedAt_ = ZonedDateTime.now()
    }

    fun markAsCompleted(completed: Boolean) {
        completed_ = completed
        this.modifiedAt_ = ZonedDateTime.now()
    }

    fun update(name: String, quantity: String, sequence: Int?, receiptId: Int?) {
        this.name_ = name
        this.quantity_ = quantity
        this.sequence_ = sequence
        this.receiptId_ = receiptId
        this.modifiedAt_ = ZonedDateTime.now()
    }

}