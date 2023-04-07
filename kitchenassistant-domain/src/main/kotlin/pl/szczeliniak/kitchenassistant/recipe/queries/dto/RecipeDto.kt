package pl.szczeliniak.kitchenassistant.recipe.queries.dto

data class RecipeDto(
    val id: Int,
    val name: String,
    val author: String?,
    val favorite: Boolean?,
    val category: CategoryDto?,
    val tags: Set<String>,
    val photoName: String?
)