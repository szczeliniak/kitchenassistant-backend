package pl.szczeliniak.kitchenassistant.receipt.commands.dto

import pl.szczeliniak.kitchenassistant.receipt.Ingredient
import pl.szczeliniak.kitchenassistant.receipt.IngredientUnit

data class NewIngredientDto(
    var name: String = "",
    var quantity: String = "",
    var unit: IngredientUnit? = null
) {
    companion object {
        fun fromDomain(ingredient: Ingredient): NewIngredientDto {
            return NewIngredientDto(
                ingredient.name,
                ingredient.quantity,
                ingredient.unit
            )
        }

    }

}