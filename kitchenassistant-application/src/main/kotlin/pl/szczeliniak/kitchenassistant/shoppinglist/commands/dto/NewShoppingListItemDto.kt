package pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto

import pl.szczeliniak.kitchenassistant.enums.IngredientUnit

data class NewShoppingListItemDto(
    var name: String = "",
    var quantity: String = "",
    var unit: IngredientUnit? = null,
    var sequence: Int? = null,
)