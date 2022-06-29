package pl.szczeliniak.kitchenassistant.receipt.queries.dto

data class IngredientDto(
    val id: Int,
    val name: String,
    val quantity: String?
)