package pl.szczeliniak.kitchenassistant.recipe.dto.request

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class UpdateRecipeRequest(
    @field:Size(min = 1, max = 100) var name: String = "",
    var categoryId: Int? = null,
    @field:Size(max = 1000) var description: String? = null,
    @field:Size(max = 50) var author: String? = null,
    @field:Size(max = 150) var source: String? = null,
    @field:Size(min = 0, max = 10) var tags: List<@NotNull String> = listOf()
)