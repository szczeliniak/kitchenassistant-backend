package pl.szczeliniak.kitchenassistant.dayplan.commands

import pl.szczeliniak.kitchenassistant.dayplan.DayPlanDao
import pl.szczeliniak.kitchenassistant.recipe.RecipeFacade
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class AddRecipeToDayPlanCommand(private val dayPlanDao: DayPlanDao, private val recipeFacade: RecipeFacade) {

    fun execute(dayPlanId: Int, recipeId: Int): SuccessResponse {
        val dayPlan = dayPlanDao.findById(dayPlanId) ?: throw KitchenAssistantException(ErrorCode.DAY_PLAN_NOT_FOUND)
        recipeFacade.getRecipe(recipeId)
        dayPlan.recipeIds.add(recipeId)
        return SuccessResponse(dayPlanDao.save(dayPlan).id)
    }
}