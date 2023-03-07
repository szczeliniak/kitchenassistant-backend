package pl.szczeliniak.kitchenassistant.recipe.queries.dto

data class StepDto(
    val id: Int,
    val name: String,
    val description: String?,
    val sequence: Int?
)