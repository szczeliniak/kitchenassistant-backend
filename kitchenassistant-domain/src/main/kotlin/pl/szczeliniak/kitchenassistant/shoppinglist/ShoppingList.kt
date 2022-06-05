package pl.szczeliniak.kitchenassistant.shoppinglist

import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.*

data class ShoppingList(
    private var id_: Int = 0,
    private var userId_: Int,
    private var name_: String,
    private var description_: String? = null,
    private var date_: LocalDate? = null,
    private var items_: MutableSet<ShoppingListItem> = mutableSetOf(),
    private var deleted_: Boolean = false,
    private var archived_: Boolean = false,
    private val createdAt_: ZonedDateTime = ZonedDateTime.now(),
    private var modifiedAt_: ZonedDateTime = ZonedDateTime.now()
) {

    val id: Int get() = id_
    val userId: Int get() = userId_
    val name: String get() = name_
    val date: LocalDate? get() = date_
    val description: String? get() = description_
    val items: Set<ShoppingListItem> get() = Collections.unmodifiableSet(items_)
    val createdAt: ZonedDateTime get() = createdAt_
    val modifiedAt: ZonedDateTime get() = modifiedAt_
    val deleted: Boolean get() = deleted_
    val archived: Boolean get() = archived_

    fun markAsDeleted() {
        if (deleted_) {
            throw KitchenAssistantException(ErrorCode.SHOPPING_LIST_ALREADY_REMOVED)
        }
        deleted_ = true
        this.modifiedAt_ = ZonedDateTime.now()
    }

    fun markAsArchived(isArchived: Boolean) {
        archived_ = isArchived
        this.modifiedAt_ = ZonedDateTime.now()
    }

    fun addItem(shoppingListItem: ShoppingListItem) {
        items_ += shoppingListItem
        this.modifiedAt_ = ZonedDateTime.now()
    }

    fun deleteItemById(shoppingListItemId: Int): ShoppingListItem {
        val item =
            items.firstOrNull { it.id == shoppingListItemId }
                ?: throw KitchenAssistantException(ErrorCode.SHOPPING_LIST_ITEM_NOT_FOUND)
        item.markAsDeleted()
        this.modifiedAt_ = ZonedDateTime.now()
        return item
    }

    fun update(name: String, description: String?, date: LocalDate?) {
        this.name_ = name
        this.description_ = description
        this.date_ = date
        this.modifiedAt_ = ZonedDateTime.now()
    }

}