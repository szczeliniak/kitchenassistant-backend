package pl.szczeliniak.kitchenassistant.dayplan.commands

import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlanDao
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanCriteria
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

open class DeleteRecipeFromDayPlansCommand(private val dayPlanDao: DayPlanDao) {

    open fun execute(recipeId: Int): SuccessResponse {
        val dayPlans = dayPlanDao.findAll(DayPlanCriteria(recipeId = recipeId))
        dayPlans.forEach { dayPlan ->
            dayPlan.recipeIds.remove(recipeId)
            if (dayPlan.recipeIds.isEmpty()) {
                dayPlanDao.delete(dayPlan)
            } else {
                dayPlanDao.save(dayPlan)
            }
        }
        return SuccessResponse(recipeId)
    }

}