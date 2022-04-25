package pl.szczeliniak.kitchenassistant.receipt

import pl.szczeliniak.kitchenassistant.shared.exceptions.NotAllowedOperationException
import java.time.LocalDateTime

data class Ingredient(
    private var id_: Int = 0,
    private var name_: String,
    private var quantity_: String,
    private var deleted_: Boolean = false,
    private val createdAt_: LocalDateTime = LocalDateTime.now(),
    private var modifiedAt_: LocalDateTime = LocalDateTime.now()
) {
    val id: Int get() = id_
    val name: String get() = name_
    val quantity: String get() = quantity_
    val deleted: Boolean get() = deleted_
    val createdAt: LocalDateTime get() = createdAt_
    val modifiedAt: LocalDateTime get() = modifiedAt_

    fun markAsDeleted() {
        if (deleted) {
            throw NotAllowedOperationException("Ingredient is already marked as deleted!")
        }
        deleted_ = true
        this.modifiedAt_ = LocalDateTime.now()
    }

    fun update(name: String, quantity: String) {
        this.name_ = name
        this.quantity_ = quantity
        this.modifiedAt_ = LocalDateTime.now()
    }

}