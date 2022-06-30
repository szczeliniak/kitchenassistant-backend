package pl.szczeliniak.kitchenassistant.dayplan.queries.dto

import java.time.LocalDate

data class DayPlanDto(
    private val id: Int,
    private val date: LocalDate
)