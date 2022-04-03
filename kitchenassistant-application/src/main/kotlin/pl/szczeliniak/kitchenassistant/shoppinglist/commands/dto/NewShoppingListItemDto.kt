package pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto

data class NewShoppingListItemDto(
    var name: String = "",
    var quantity: String = "",
    var sequence: Int? = null,
)