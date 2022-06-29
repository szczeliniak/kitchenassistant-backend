package pl.szczeliniak.kitchenassistant.receipt.commands.dto

import org.hibernate.validator.constraints.Length

data class UpdateIngredientDto(
    @field:Length(min = 1, max = 100) var name: String = "",
    @field:Length(min = 1, max = 50) var quantity: String? = null,
    var ingredientGroupId: Int = 0
)