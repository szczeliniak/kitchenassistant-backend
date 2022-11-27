package pl.szczeliniak.kitchenassistant.dayplan.queries.dto

import java.time.LocalDate

data class DayPlanDetailsDto(
    val id: Int,
    val date: LocalDate,
    val recipes: List<SimpleRecipeDto>,
    val automaticArchiving: Boolean
)