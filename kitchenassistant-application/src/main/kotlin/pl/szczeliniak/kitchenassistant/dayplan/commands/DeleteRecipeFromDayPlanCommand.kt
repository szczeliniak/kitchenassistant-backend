package pl.szczeliniak.kitchenassistant.dayplan.commands

import pl.szczeliniak.kitchenassistant.dayplan.DayPlanDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class DeleteRecipeFromDayPlanCommand(private val dayPlanDao: DayPlanDao) {

    fun execute(dayPlanId: Int, recipeId: Int): SuccessResponse {
        val dayPlan = dayPlanDao.findById(dayPlanId) ?: throw KitchenAssistantException(ErrorCode.DAY_PLAN_NOT_FOUND)
        if (!dayPlan.recipeIds.contains(recipeId)) {
            throw KitchenAssistantException(ErrorCode.RECIPE_ID_IS_NOT_ASSIGNED_TO_DAY_PLAN)
        }
        dayPlan.recipeIds.remove(recipeId)
        return SuccessResponse(dayPlanDao.save(dayPlan).id)
    }

}