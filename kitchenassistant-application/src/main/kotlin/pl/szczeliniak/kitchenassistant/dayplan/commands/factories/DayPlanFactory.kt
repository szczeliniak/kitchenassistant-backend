package pl.szczeliniak.kitchenassistant.dayplan.commands.factories

import pl.szczeliniak.kitchenassistant.dayplan.DayPlan
import pl.szczeliniak.kitchenassistant.dayplan.commands.dto.NewDayPlanDto

open class DayPlanFactory {

    open fun create(dto: NewDayPlanDto): DayPlan {
        return DayPlan(userId_ = dto.userId, date_ = dto.date)
    }

}