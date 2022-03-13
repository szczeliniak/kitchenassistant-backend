package pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto

import pl.szczeliniak.kitchenassistant.enums.IngredientUnit
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListItem
import java.time.LocalDateTime

data class ShoppingListItemDto(
    val id: Int,
    val name: String,
    val quantity: String,
    val unit: IngredientUnit?,
    val sequence: Int?,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime
) {

    companion object {
        fun fromDomain(shoppingListItem: ShoppingListItem): ShoppingListItemDto {
            return ShoppingListItemDto(
                shoppingListItem.id,
                shoppingListItem.name,
                shoppingListItem.quantity,
                shoppingListItem.unit,
                shoppingListItem.sequence,
                shoppingListItem.createdAt,
                shoppingListItem.modifiedAt
            )
        }
    }

}