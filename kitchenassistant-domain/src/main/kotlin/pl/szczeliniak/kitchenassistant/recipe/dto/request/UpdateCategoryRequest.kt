package pl.szczeliniak.kitchenassistant.recipe.dto.request

import javax.validation.constraints.Size

data class UpdateCategoryRequest(
    @field:Size(min = 1, max = 50) var name: String = "",
    var sequence: Int? = null
)