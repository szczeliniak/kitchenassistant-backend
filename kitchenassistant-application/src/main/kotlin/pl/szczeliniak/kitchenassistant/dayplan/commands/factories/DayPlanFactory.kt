package pl.szczeliniak.kitchenassistant.dayplan.commands.factories

import pl.szczeliniak.kitchenassistant.dayplan.DayPlan
import pl.szczeliniak.kitchenassistant.dayplan.commands.dto.NewDayPlanDto

open class DayPlanFactory {

    open fun create(dto: NewDayPlanDto): DayPlan {
        return DayPlan(name = dto.name, description = dto.description, userId = dto.userId, date = dto.date)
    }

}