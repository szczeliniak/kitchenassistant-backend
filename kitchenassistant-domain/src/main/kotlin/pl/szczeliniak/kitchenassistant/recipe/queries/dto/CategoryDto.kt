package pl.szczeliniak.kitchenassistant.recipe.queries.dto

data class CategoryDto(
    val id: Int,
    val name: String,
    var sequence: Int?
)