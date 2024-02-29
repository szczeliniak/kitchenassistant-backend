package pl.szczeliniak.kitchenassistant.recipe.dto.request

import org.hibernate.validator.constraints.Length

data class NewStepRequest(
    @field:Length(max = 1000) var description: String,
    var sequence: Int? = null
)