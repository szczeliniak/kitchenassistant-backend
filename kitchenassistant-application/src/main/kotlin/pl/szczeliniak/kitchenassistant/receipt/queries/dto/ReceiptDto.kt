package pl.szczeliniak.kitchenassistant.receipt.queries.dto

import pl.szczeliniak.kitchenassistant.receipt.Receipt

data class ReceiptDto(
    val id: Int,
    val name: String,
    val description: String?,
    val author: String?,
    val source: String?,
    val category: CategoryDto?,
    val ingredients: Set<IngredientDto>,
    val steps: Set<StepDto>,
    val photos: Set<PhotoDto>,
    val tags: Set<String>
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
                receipt.ingredients.map { IngredientDto.fromDomain(it) }.toSet(),
                receipt.steps.map { StepDto.fromDomain(it) }.toSet(),
                receipt.photos.map { PhotoDto.fromDomain(it) }.toSet(),
                receipt.tags.map { it.name }.toSet()
            )
        }
    }
}