package pl.szczeliniak.kitchenassistant.recipe.queries

import pl.szczeliniak.kitchenassistant.recipe.db.Category
import pl.szczeliniak.kitchenassistant.recipe.db.Recipe
import pl.szczeliniak.kitchenassistant.recipe.db.Step
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.CategoryDto
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.RecipeDetailsDto
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.RecipeDto
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.StepDto

open class RecipeConverter(private val ingredientGroupConverter: IngredientGroupConverter) {

    open fun map(recipe: Recipe): RecipeDto {
        return RecipeDto(
            recipe.id,
            recipe.name,
            recipe.author?.name,
            recipe.favorite,
            recipe.category?.let { map(it) },
            recipe.tags.map { it.name }.toSet(),
            recipe.photoName
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
            recipe.ingredientGroups.map { ingredientGroupConverter.map(it) }.toSet(),
            recipe.steps.map { map(it) }.toSet(),
            recipe.photoName,
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

    private fun map(step: Step): StepDto {
        return StepDto(
            step.id,
            step.name,
            step.description,
            step.sequence
        )
    }

}