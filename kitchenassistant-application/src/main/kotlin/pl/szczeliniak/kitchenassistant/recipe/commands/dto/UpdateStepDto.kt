package pl.szczeliniak.kitchenassistant.recipe.commands.dto

import org.hibernate.validator.constraints.Length

data class UpdateStepDto(
    @field:Length(min = 1, max = 100) var name: String = "",
    @field:Length(max = 1000) var description: String? = null,
    var sequence: Int? = null
)