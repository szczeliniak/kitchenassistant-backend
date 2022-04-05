package pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto

data class UpdateShoppingListItemDto(
    var name: String = "",
    var quantity: String = "",
    var sequence: Int? = null
)