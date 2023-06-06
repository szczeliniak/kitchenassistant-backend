package pl.szczeliniak.kitchenassistant.dayplan.commands

import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlanDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class DeleteRecipeFromDayPlanCommand(private val dayPlanDao: DayPlanDao) {

    fun execute(dayPlanId: Int, recipeId: Int): SuccessResponse {
        dayPlanDao.findById(dayPlanId)?.let { dayPlan ->
            dayPlan.recipes.removeIf { it.id == recipeId }
            if (dayPlan.recipes.isEmpty()) {
                dayPlanDao.delete(dayPlan)
            } else {
                dayPlanDao.save(dayPlan)
            }
        }
        return SuccessResponse(dayPlanId)
    }

}