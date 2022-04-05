package pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto

import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingList
import java.time.LocalDate

data class ShoppingListDto(
    val id: Int,
    val name: String,
    val description: String?,
    val date: LocalDate?,
    val archived: Boolean,
    val items: List<ShoppingListItemDto>
) {

    companion object {
        fun fromDomain(shoppingList: ShoppingList): ShoppingListDto {
            return ShoppingListDto(
                shoppingList.id,
                shoppingList.name,
                shoppingList.description,
                shoppingList.date,
                shoppingList.archived,
                shoppingList.items.map { ShoppingListItemDto.fromDomain(it) }
            )
        }
    }

}