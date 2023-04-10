package pl.szczeliniak.kitchenassistant.recipe.commands.dto

import org.hibernate.validator.constraints.Length

data class UpdateIngredientDto(
    var id: Int? = null,
    @field:Length(min = 1, max = 100) var name: String = "",
    @field:Length(min = 1, max = 50) var quantity: String? = null,
)