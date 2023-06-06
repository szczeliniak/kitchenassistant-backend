package pl.szczeliniak.kitchenassistant.dayplan.queries

import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlan
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.*
import pl.szczeliniak.kitchenassistant.recipe.db.Ingredient
import pl.szczeliniak.kitchenassistant.recipe.db.IngredientGroup
import pl.szczeliniak.kitchenassistant.recipe.db.Recipe

open class DayPlanConverter {

    open fun map(dayPlan: DayPlan): DayPlanDto {
        return DayPlanDto(
            dayPlan.id,
            dayPlan.date,
            dayPlan.automaticArchiving
        )
    }

    open fun mapDetails(dayPlan: DayPlan): DayPlanDetailsDto {
        return DayPlanDetailsDto(
            dayPlan.id,
            dayPlan.date,
            dayPlan.recipes.map { mapRecipe(it) },
            dayPlan.automaticArchiving
        )
    }

    private fun mapRecipe(recipe: Recipe): SimpleRecipeDto {
        return SimpleRecipeDto(
            recipe.id,
            recipe.name,
            recipe.author?.name,
            recipe.category?.name,
            recipe.ingredientGroups.map { map(it) }
        )
    }

    private fun map(ingredientGroup: IngredientGroup): DayPlanIngredientGroupDto {
        return DayPlanIngredientGroupDto(ingredientGroup.name, ingredientGroup.ingredients.map { map(it) })
    }

    private fun map(ingredient: Ingredient): DayPlanIngredientDto {
        return DayPlanIngredientDto(ingredient.name, ingredient.quantity)
    }

}