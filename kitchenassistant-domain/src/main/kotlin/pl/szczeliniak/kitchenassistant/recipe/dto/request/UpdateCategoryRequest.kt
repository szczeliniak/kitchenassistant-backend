package pl.szczeliniak.kitchenassistant.recipe.dto.request

import javax.validation.constraints.Size

data class UpdateCategoryRequest(
    @field:Size(min = 1, max = 255) var name: String = ""
)