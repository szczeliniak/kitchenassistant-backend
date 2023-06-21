package pl.szczeliniak.kitchenassistant.recipe.commands.dto

import org.hibernate.validator.constraints.Length

data class NewStepRequest(
    @field:Length(max = 1000) var description: String? = null,
    @field:Length(max = 100) var photoName: String? = null,
    var sequence: Int? = null
)