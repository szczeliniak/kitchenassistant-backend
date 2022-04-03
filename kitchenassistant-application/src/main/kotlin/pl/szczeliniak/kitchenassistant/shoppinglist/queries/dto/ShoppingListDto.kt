package pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto

import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingList

data class ShoppingListDto(
    val id: Int,
    val userId: Int,
    val name: String,
    val description: String?,
    val items: List<ShoppingListItemDto>
) {

    companion object {
        fun fromDomain(shoppingList: ShoppingList): ShoppingListDto {
            return ShoppingListDto(
                shoppingList.id,
                shoppingList.userId,
                shoppingList.name,
                shoppingList.description,
                shoppingList.items.map { ShoppingListItemDto.fromDomain(it) }
            )
        }
    }

}