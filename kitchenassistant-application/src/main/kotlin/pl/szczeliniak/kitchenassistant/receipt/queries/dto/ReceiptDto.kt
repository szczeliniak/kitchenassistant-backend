package pl.szczeliniak.kitchenassistant.receipt.queries.dto

import pl.szczeliniak.kitchenassistant.receipt.Receipt

data class ReceiptDto(
    val id: Int,
    val name: String,
    val description: String?,
    val author: String?,
    val source: String?,
    val category: CategoryDto?,
    val ingredients: List<IngredientDto>,
    val steps: List<StepDto>,
    val photos: List<String>
) {
    companion object {
        fun fromDomain(receipt: Receipt): ReceiptDto {
            return ReceiptDto(
                receipt.id,
                receipt.name,
                receipt.description,
                receipt.author,
                receipt.source,
                receipt.category?.let { CategoryDto.fromDomain(it) },
                receipt.ingredients.map { IngredientDto.fromDomain(it) },
                receipt.steps.map { StepDto.fromDomain(it) },
                receipt.photos.map { it.name }
            )
        }
    }
}