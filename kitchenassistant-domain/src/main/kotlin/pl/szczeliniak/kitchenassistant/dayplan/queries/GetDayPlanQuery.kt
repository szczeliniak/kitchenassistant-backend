package pl.szczeliniak.kitchenassistant.dayplan.queries

import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlanDao
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanResponse
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException

class GetDayPlanQuery(private val dayPlanDao: DayPlanDao, private val dayPlanConverter: DayPlanConverter) {

    fun execute(dayPlanId: Int): DayPlanResponse {
        return DayPlanResponse(
            dayPlanConverter.mapDetails(
                dayPlanDao.findById(dayPlanId) ?: throw KitchenAssistantException(ErrorCode.DAY_PLAN_NOT_FOUND)
            )
        )
    }

}