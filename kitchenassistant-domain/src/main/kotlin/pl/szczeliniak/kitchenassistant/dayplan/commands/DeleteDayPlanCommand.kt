package pl.szczeliniak.kitchenassistant.dayplan.commands

import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlanDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class DeleteDayPlanCommand(private val dayPlanDao: DayPlanDao) {

    fun execute(dayPlanId: Int): SuccessResponse {
        dayPlanDao.findById(dayPlanId)?.let { dayPlanDao.delete(it) }
        return SuccessResponse(dayPlanId)
    }

}