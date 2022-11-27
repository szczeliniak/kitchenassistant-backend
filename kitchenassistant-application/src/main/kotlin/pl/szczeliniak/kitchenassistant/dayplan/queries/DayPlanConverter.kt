package pl.szczeliniak.kitchenassistant.dayplan.queries

import pl.szczeliniak.kitchenassistant.dayplan.DayPlan
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.*
import pl.szczeliniak.kitchenassistant.recipe.RecipeFacade
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.IngredientDto
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.IngredientGroupDto
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.RecipeDetailsDto
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.RecipeResponse

open class DayPlanConverter(private val recipeFacade: RecipeFacade) {

    open fun map(dayPlan: DayPlan): DayPlanDto {
        return DayPlanDto(
            dayPlan.id,
            dayPlan.date
        )
    }

    open fun mapDetails(dayPlan: DayPlan): DayPlanDetailsDto {
        return DayPlanDetailsDto(
            dayPlan.id,
            dayPlan.date,
            dayPlan.recipeIds.map { mapRecipe(recipeFacade.getRecipe(it)) },
            dayPlan.automaticArchiving
        )
    }

    private fun mapRecipe(response: RecipeResponse): SimpleRecipeDto {
        return SimpleRecipeDto(
            response.recipe.id,
            response.recipe.name,
            response.recipe.author,
            response.recipe.category?.name
        )
    }

    fun map(recipeDto: RecipeDetailsDto): DayPlanRecipeDto {
        return DayPlanRecipeDto(
            recipeDto.id,
            recipeDto.name,
            recipeDto.ingredientGroups.map { map(it) },
            recipeDto.author
        )
    }

    private fun map(ingredientGroupDto: IngredientGroupDto): DayPlanIngredientGroupDto {
        return DayPlanIngredientGroupDto(ingredientGroupDto.name, ingredientGroupDto.ingredients.map { map(it) })
    }

    private fun map(ingredientDto: IngredientDto): DayPlanIngredientDto {
        return DayPlanIngredientDto(ingredientDto.name, ingredientDto.quantity)
    }

}