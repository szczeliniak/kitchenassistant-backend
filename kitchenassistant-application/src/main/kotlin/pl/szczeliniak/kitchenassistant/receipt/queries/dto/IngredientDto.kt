package pl.szczeliniak.kitchenassistant.receipt.queries.dto

import pl.szczeliniak.kitchenassistant.receipt.Ingredient

data class IngredientDto(
    val id: Int,
    val name: String,
    val quantity: String
) {
    companion object {
        fun fromDomain(ingredient: Ingredient): IngredientDto {
            return IngredientDto(
                ingredient.id,
                ingredient.name,
                ingredient.quantity
            )
        }
    }
}