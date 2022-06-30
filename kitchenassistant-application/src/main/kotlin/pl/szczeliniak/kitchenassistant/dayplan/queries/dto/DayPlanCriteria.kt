package pl.szczeliniak.kitchenassistant.dayplan.queries.dto

data class DayPlanCriteria(
    val userId: Int?,
    val archived: Boolean?
)