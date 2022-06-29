package pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto

import org.hibernate.validator.constraints.Length

data class NewShoppingListItemDto(
    @field:Length(min = 1, max = 100) var name: String = "",
    @field:Length(min = 1, max = 50) var quantity: String? = null,
    var sequence: Int? = null,
    var receiptId: Int? = null
)