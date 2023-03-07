package pl.szczeliniak.kitchenassistant.recipe.queries

import pl.szczeliniak.kitchenassistant.recipe.db.*
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.*

open class RecipeConverter {

    open fun map(recipe: Recipe): RecipeDto {
        return RecipeDto(
            recipe.id,
            recipe.name,
            recipe.author?.name,
            recipe.favorite,
            recipe.category?.let { map(it) },
            recipe.tags.map { it.name }.toSet()
        )
    }

    open fun mapDetails(recipe: Recipe): RecipeDetailsDto {
        return RecipeDetailsDto(
            recipe.id,
            recipe.name,
            recipe.description,
            recipe.author?.name,
            recipe.source,
            recipe.favorite,
            recipe.category?.let { map(it) },
            recipe.ingredientGroups.map { map(it) }.toSet(),
            recipe.steps.map { map(it) }.toSet(),
            recipe.photos.map { map(it) }.toSet(),
            recipe.tags.map { it.name }.toSet()
        )
    }

    open fun map(category: Category): CategoryDto {
        return CategoryDto(
            category.id,
            category.name,
            category.sequence
        )
    }

    private fun map(ingredient: Ingredient): IngredientDto {
        return IngredientDto(
            ingredient.id,
            ingredient.name,
            ingredient.quantity
        )
    }

    private fun map(photo: Photo): PhotoDto {
        return PhotoDto(photo.id, photo.name)
    }

    private fun map(step: Step): StepDto {
        return StepDto(
            step.id,
            step.name,
            step.description,
            step.sequence
        )
    }

    private fun map(ingredientGroup: IngredientGroup): IngredientGroupDto {
        return IngredientGroupDto(
            ingredientGroup.id,
            ingredientGroup.name,
            ingredientGroup.ingredients.map { map(it) }.toSet()
        )
    }

}