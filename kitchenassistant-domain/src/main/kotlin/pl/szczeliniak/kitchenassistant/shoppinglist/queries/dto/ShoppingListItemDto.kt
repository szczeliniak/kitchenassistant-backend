package pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto

data class ShoppingListItemDto(
    val id: Int,
    val name: String,
    val quantity: String?,
    val sequence: Int?,
    val completed: Boolean,
    val recipe: SimpleRecipeDto?
)