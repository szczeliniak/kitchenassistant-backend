package pl.szczeliniak.kitchenassistant.receipt.queries.dto

data class ReceiptDetailsDto(
    val id: Int,
    val name: String,
    val description: String?,
    val author: String?,
    val source: String?,
    val favorite: Boolean?,
    val category: CategoryDto?,
    val ingredientGroups: Set<IngredientGroupDto>,
    val steps: Set<StepDto>,
    val photos: Set<PhotoDto>,
    val tags: Set<String>
)