package pl.szczeliniak.kitchenassistant.dayplan.commands

import pl.szczeliniak.kitchenassistant.dayplan.DayPlanDao
import pl.szczeliniak.kitchenassistant.dayplan.commands.dto.NewDayPlanDto
import pl.szczeliniak.kitchenassistant.dayplan.commands.factories.DayPlanFactory
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanCriteria
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import java.time.LocalDate

class AddDayPlanCommand(private val dayPlanDao: DayPlanDao, private val dayPlanFactory: DayPlanFactory) {

    fun execute(dto: NewDayPlanDto): SuccessResponse {
        if (dto.date.isBefore(LocalDate.now())) {
            throw KitchenAssistantException(ErrorCode.DAY_PLAN_PAST_DATE)
        } else if (dayPlanDao.findAll(DayPlanCriteria(since = dto.date, to = dto.date)).isNotEmpty()) {
            throw KitchenAssistantException(ErrorCode.DAY_PLAN_FOT_DATE_ALREADY_EXISTS)
        }
        return SuccessResponse(dayPlanDao.save(dayPlanFactory.create(dto)).id)
    }

}