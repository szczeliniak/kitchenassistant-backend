package pl.szczeliniak.kitchenassistant.recipe.commands.dto

import org.hibernate.validator.constraints.Length

data class UpdateStepRequest(
    @field:Length(max = 1000) var description: String? = null,
    var sequence: Int? = null
)