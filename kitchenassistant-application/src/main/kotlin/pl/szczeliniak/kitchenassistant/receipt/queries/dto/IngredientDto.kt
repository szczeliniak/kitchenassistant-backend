package pl.szczeliniak.kitchenassistant.receipt.queries.dto

import pl.szczeliniak.kitchenassistant.receipt.Ingredient
import pl.szczeliniak.kitchenassistant.receipt.IngredientUnit
import java.time.LocalDateTime

data class IngredientDto(
    val id: Int,
    val name: String,
    val quantity: String,
    val unit: IngredientUnit?,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime
) {
    companion object {
        fun fromDomain(ingredient: Ingredient): IngredientDto {
            return IngredientDto(
                ingredient.id,
                ingredient.name,
                ingredient.quantity,
                ingredient.unit,
                ingredient.createdAt,
                ingredient.modifiedAt
            )
        }
    }
}