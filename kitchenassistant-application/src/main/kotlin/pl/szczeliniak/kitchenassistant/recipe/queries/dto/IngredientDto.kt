package pl.szczeliniak.kitchenassistant.recipe.queries.dto

data class IngredientDto(
    val id: Int,
    val name: String,
    val quantity: String?
)