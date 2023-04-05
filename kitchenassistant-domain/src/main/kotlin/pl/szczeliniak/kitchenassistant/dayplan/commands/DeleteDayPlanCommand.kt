package pl.szczeliniak.kitchenassistant.dayplan.commands

import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlanDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class DeleteDayPlanCommand(private val dayPlanDao: DayPlanDao) {

    fun execute(dayPlanId: Int): SuccessResponse {
        val dayPlan = dayPlanDao.findById(dayPlanId) ?: throw KitchenAssistantException(ErrorCode.DAY_PLAN_NOT_FOUND)
        if (dayPlan.deleted) {
            throw KitchenAssistantException(ErrorCode.DAY_PLAN_ALREADY_REMOVED)
        }
        dayPlan.deleted = true
        return SuccessResponse(dayPlanDao.save(dayPlan).id)
    }

}