package pl.szczeliniak.kitchenassistant.dayplan.queries.dto

import pl.szczeliniak.kitchenassistant.shared.dtos.Pagination

data class DayPlansResponse(
    val dayPlans: List<DayPlanDto>,
    val pagination: Pagination
)