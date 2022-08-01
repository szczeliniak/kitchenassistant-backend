package pl.szczeliniak.kitchenassistant.dayplan.queries

import pl.szczeliniak.kitchenassistant.dayplan.DayPlanDao
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanRecipesResponse
import pl.szczeliniak.kitchenassistant.recipe.queries.GetRecipeQuery
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.RecipeDetailsDto
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException

class GetRecipesByDayPlanIdQuery(
    private val dayPlanDao: DayPlanDao,
    private val getRecipeQuery: GetRecipeQuery,
    private val dayPlanConverter: DayPlanConverter
) {

    fun execute(dayPlanId: Int): DayPlanRecipesResponse {
        val dayPlan = dayPlanDao.findById(dayPlanId) ?: throw KitchenAssistantException(ErrorCode.DAY_PLAN_NOT_FOUND)
        return DayPlanRecipesResponse(
            dayPlan.recipeIds.mapNotNull { getRecipe(it) }.map { dayPlanConverter.map(it) })
    }

    private fun getRecipe(it: Int): RecipeDetailsDto? {
        try {
            return getRecipeQuery.execute(it).recipe
        } catch (_: KitchenAssistantException) {
        }
        return null
    }

}