package pl.szczeliniak.kitchenassistant.receipt.queries.dto

data class IngredientGroupDto(
    val id: Int,
    val name: String,
    val ingredients: Set<IngredientDto>
)