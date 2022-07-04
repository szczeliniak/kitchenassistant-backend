package pl.szczeliniak.kitchenassistant.dayplan.commands

import pl.szczeliniak.kitchenassistant.dayplan.DayPlanDao
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanCriteria
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

open class DeassignReceiptsFromDayPlansCommand(private val dayPlanDao: DayPlanDao) {

    open fun execute(receiptId: Int): SuccessResponse {
        val dayPlans = dayPlanDao.findAll(DayPlanCriteria(receiptId = receiptId))
        dayPlans.forEach { dayPlan -> dayPlan.receiptIds.remove(receiptId) }
        dayPlanDao.save(dayPlans)
        return SuccessResponse(receiptId)
    }

}