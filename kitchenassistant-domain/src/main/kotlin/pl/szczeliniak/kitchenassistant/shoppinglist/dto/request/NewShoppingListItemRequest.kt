package pl.szczeliniak.kitchenassistant.shoppinglist.dto.request

import org.hibernate.validator.constraints.Length

data class NewShoppingListItemRequest(
    @field:Length(min = 1, max = 100) var name: String = "",
    @field:Length(min = 1, max = 50) var quantity: String? = null,
    var sequence: Int? = null,
    var recipeId: Int? = null
)