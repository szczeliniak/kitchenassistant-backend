package pl.szczeliniak.kitchenassistant.recipe.dto.request

import org.hibernate.validator.constraints.Length

data class NewCategoryRequest(
    @field:Length(min = 1, max = 100) var name: String = "",
    var sequence: Int? = null
)