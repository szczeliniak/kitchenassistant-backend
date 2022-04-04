package pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto

import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListItem

data class ShoppingListItemDto(
    val id: Int,
    val name: String,
    val quantity: String,
    val sequence: Int?,
    val done: Boolean
) {

    companion object {
        fun fromDomain(shoppingListItem: ShoppingListItem): ShoppingListItemDto {
            return ShoppingListItemDto(
                shoppingListItem.id,
                shoppingListItem.name,
                shoppingListItem.quantity,
                shoppingListItem.sequence,
                shoppingListItem.done
            )
        }
    }

}