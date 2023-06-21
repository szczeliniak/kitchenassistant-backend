package pl.szczeliniak.kitchenassistant.recipe.queries.dto

data class StepDto(
    val id: Int,
    val description: String?,
    val sequence: Int?
)