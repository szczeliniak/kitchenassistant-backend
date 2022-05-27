package pl.szczeliniak.kitchenassistant.receipt

import pl.szczeliniak.kitchenassistant.shared.exceptions.NotAllowedOperationException
import java.time.ZonedDateTime

data class Ingredient(
    private var id_: Int = 0,
    private var name_: String,
    private var quantity_: String,
    private var deleted_: Boolean = false,
    private val createdAt_: ZonedDateTime = ZonedDateTime.now(),
    private var modifiedAt_: ZonedDateTime = ZonedDateTime.now()
) {
    val id: Int get() = id_
    val name: String get() = name_
    val quantity: String get() = quantity_
    val deleted: Boolean get() = deleted_
    val createdAt: ZonedDateTime get() = createdAt_
    val modifiedAt: ZonedDateTime get() = modifiedAt_

    fun markAsDeleted() {
        if (deleted) {
            throw NotAllowedOperationException("Ingredient is already marked as deleted!")
        }
        deleted_ = true
        this.modifiedAt_ = ZonedDateTime.now()
    }

    fun update(name: String, quantity: String) {
        this.name_ = name
        this.quantity_ = quantity
        this.modifiedAt_ = ZonedDateTime.now()
    }

}