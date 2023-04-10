package pl.szczeliniak.kitchenassistant.recipe.queries

import pl.szczeliniak.kitchenassistant.recipe.db.Ingredient
import pl.szczeliniak.kitchenassistant.recipe.db.IngredientGroup
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.IngredientDto
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.IngredientGroupDto

open class IngredientGroupConverter {

    open fun map(ingredientGroup: IngredientGroup): IngredientGroupDto {
        return IngredientGroupDto(
            ingredientGroup.id,
            ingredientGroup.name,
            ingredientGroup.ingredients.map { map(it) }.toSet()
        )
    }

    private fun map(ingredient: Ingredient): IngredientDto {
        return IngredientDto(
            ingredient.id,
            ingredient.name,
            ingredient.quantity
        )
    }

}