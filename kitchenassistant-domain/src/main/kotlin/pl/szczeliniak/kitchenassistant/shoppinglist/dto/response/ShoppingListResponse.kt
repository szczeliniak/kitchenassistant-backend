package pl.szczeliniak.kitchenassistant.shoppinglist.dto.response

import java.time.LocalDate

data class ShoppingListResponse(
    val shoppingList: ShoppingListDetailsDto
) {
    data class ShoppingListDetailsDto(
        val id: Int,
        val name: String,
        val description: String?,
        val date: LocalDate?,
        val items: Set<ShoppingListItemDto>
    ) {
        data class ShoppingListItemDto(
            val id: Int,
            val name: String,
            val quantity: String?,
            val sequence: Int?,
            val completed: Boolean,
            val recipe: SimpleRecipeDto?
        ) {
            data class SimpleRecipeDto(
                val id: Int,
                val name: String
            )
        }
    }
}