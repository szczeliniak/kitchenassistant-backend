package pl.szczeliniak.kitchenassistant.receipt.queries.dto

import pl.szczeliniak.kitchenassistant.receipt.Category

data class CategoryDto(
    val id: Int,
    val name: String
) {
    companion object {
        fun fromDomain(category: Category): CategoryDto {
            return CategoryDto(
                category.id,
                category.name
            )
        }
    }
}