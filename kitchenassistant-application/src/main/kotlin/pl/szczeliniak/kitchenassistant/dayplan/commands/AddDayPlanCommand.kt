package pl.szczeliniak.kitchenassistant.dayplan.commands

import pl.szczeliniak.kitchenassistant.dayplan.DayPlanDao
import pl.szczeliniak.kitchenassistant.dayplan.commands.dto.NewDayPlanDto
import pl.szczeliniak.kitchenassistant.dayplan.commands.factories.DayPlanFactory
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class AddDayPlanCommand(private val dayPlanDao: DayPlanDao, private val dayPlanFactory: DayPlanFactory) {

    fun execute(dto: NewDayPlanDto): SuccessResponse {
        return SuccessResponse(dayPlanDao.save(dayPlanFactory.create(dto)).id)
    }

}