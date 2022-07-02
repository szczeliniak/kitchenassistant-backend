package pl.szczeliniak.kitchenassistant.dayplan.queries.dto

import java.time.LocalDate

data class DayPlanDto(
    val id: Int,
    val date: LocalDate
)