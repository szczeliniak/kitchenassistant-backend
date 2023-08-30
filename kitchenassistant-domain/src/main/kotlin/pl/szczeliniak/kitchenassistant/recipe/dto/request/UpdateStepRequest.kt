package pl.szczeliniak.kitchenassistant.recipe.dto.request

import org.hibernate.validator.constraints.Length

data class UpdateStepRequest(
    @field:Length(max = 1000) var description: String,
    @field:Length(max = 100) var photoName: String? = null,
    var sequence: Int? = null
)