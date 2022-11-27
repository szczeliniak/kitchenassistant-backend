package pl.szczeliniak.kitchenassistant.dayplan.commands

import pl.szczeliniak.kitchenassistant.dayplan.DayPlanDao
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanCriteria
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

open class DeassignRecipesFromDayPlansCommand(private val dayPlanDao: DayPlanDao) {

    open fun execute(recipeId: Int): SuccessResponse {
        val dayPlans = dayPlanDao.findAll(DayPlanCriteria(recipeId = recipeId))
        dayPlans.forEach { dayPlan ->
            dayPlan.recipeIds.remove(recipeId)
            if (dayPlan.recipeIds.isEmpty()) {
                dayPlan.deleted = true
            }
        }

        dayPlanDao.save(dayPlans)
        return SuccessResponse(recipeId)
    }

}