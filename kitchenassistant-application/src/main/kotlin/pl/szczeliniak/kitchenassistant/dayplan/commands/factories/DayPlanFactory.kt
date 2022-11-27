package pl.szczeliniak.kitchenassistant.dayplan.commands.factories

import pl.szczeliniak.kitchenassistant.dayplan.DayPlan
import pl.szczeliniak.kitchenassistant.dayplan.commands.dto.AddRecipeToDayPlanDto

open class DayPlanFactory {

    open fun create(dto: AddRecipeToDayPlanDto): DayPlan {
        return DayPlan(
            userId = dto.userId,
            date = dto.date,
            automaticArchiving = false
        )
    }

}