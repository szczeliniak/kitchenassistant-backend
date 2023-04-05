package pl.szczeliniak.kitchenassistant.dayplan.commands

import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlanDao
import pl.szczeliniak.kitchenassistant.dayplan.commands.dto.UpdateDayPlanDto
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanCriteria
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class UpdateDayPlanCommand(private val dayPlanDao: DayPlanDao) {

    fun execute(dayPlanId: Int, request: UpdateDayPlanDto): SuccessResponse {
        val dayPlan = dayPlanDao.findById(dayPlanId) ?: throw KitchenAssistantException(ErrorCode.DAY_PLAN_NOT_FOUND)

        dayPlanDao.findAll(DayPlanCriteria(dayPlan.userId, null, null, request.date, request.date, null))
                .filter { dp -> dp.id != dayPlan.id}
                .any { throw KitchenAssistantException(ErrorCode.DAY_PLAN_ALREADY_EXISTS) }

        dayPlan.automaticArchiving = request.automaticArchiving
        dayPlan.date = request.date

        return SuccessResponse(dayPlanDao.save(dayPlan).id)
    }

}