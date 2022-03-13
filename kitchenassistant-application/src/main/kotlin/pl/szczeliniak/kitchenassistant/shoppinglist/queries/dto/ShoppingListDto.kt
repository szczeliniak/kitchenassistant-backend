package pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto

import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingList
import java.time.LocalDateTime

data class ShoppingListDto(
    val id: Int,
    val userId: Int,
    val title: String,
    val description: String?,
    val items: List<ShoppingListItemDto>,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime
) {

    companion object {
        fun fromDomain(shoppingList: ShoppingList): ShoppingListDto {
            return ShoppingListDto(
                shoppingList.id,
                shoppingList.userId,
                shoppingList.title,
                shoppingList.description,
                shoppingList.items.map { ShoppingListItemDto.fromDomain(it) },
                shoppingList.createdAt,
                shoppingList.modifiedAt
            )
        }
    }

}