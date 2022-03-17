package pl.szczeliniak.kitchenassistant.shoppinglist

import pl.szczeliniak.kitchenassistant.exceptions.NotAllowedOperationException
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import java.time.LocalDateTime
import java.util.*

data class ShoppingList(
    private var id_: Int = 0,
    private var userId_: Int,
    private var title_: String,
    private var description_: String?,
    private var items_: MutableList<ShoppingListItem> = mutableListOf(),
    private var deleted_: Boolean = false,
    private val createdAt_: LocalDateTime = LocalDateTime.now(),
    private var modifiedAt_: LocalDateTime = LocalDateTime.now()
) {

    val id: Int get() = id_
    val userId: Int get() = userId_
    val title: String get() = title_
    val description: String? get() = description_
    val items: List<ShoppingListItem> get() = Collections.unmodifiableList(items_)
    val createdAt: LocalDateTime get() = createdAt_
    val modifiedAt: LocalDateTime get() = modifiedAt_
    val deleted: Boolean get() = deleted_

    fun markAsDeleted() {
        if (deleted_) {
            throw NotAllowedOperationException("Shopping list is already marked as deleted!")
        }
        deleted_ = true
        this.modifiedAt_ = LocalDateTime.now()
    }

    fun addItem(shoppingListItem: ShoppingListItem) {
        items_ += shoppingListItem
        this.modifiedAt_ = LocalDateTime.now()
    }

    fun deleteItemById(shoppingListItemId: Int): ShoppingListItem {
        val item =
            items.firstOrNull { it.id == shoppingListItemId } ?: throw NotFoundException("Shopping list item not found")
        item.markAsDeleted()
        this.modifiedAt_ = LocalDateTime.now()
        return item
    }

}