package pl.szczeliniak.kitchenassistant.dayplan.queries

import pl.szczeliniak.kitchenassistant.dayplan.DayPlan
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanDetailsDto
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanDto

open class DayPlanConverter {

    open fun map(dayPlan: DayPlan): DayPlanDto {
        return DayPlanDto(
            dayPlan.id,
            dayPlan.date
        )
    }

    open fun mapDetails(dayPlan: DayPlan): DayPlanDetailsDto {
        return DayPlanDetailsDto(
            dayPlan.id,
            dayPlan.date,
            dayPlan.receiptIds
        )
    }

}