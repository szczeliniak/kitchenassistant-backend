package pl.szczeliniak.kitchenassistant.receipt.queries.dto

data class ReceiptDto(
    val id: Int,
    val name: String,
    val description: String?,
    val author: String?,
    val source: String?,
    val favorite: Boolean?,
    val category: CategoryDto?,
    val ingredients: Set<IngredientDto>,
    val steps: Set<StepDto>,
    val photos: Set<PhotoDto>,
    val tags: Set<String>
)