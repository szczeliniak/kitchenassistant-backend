package pl.szczeliniak.kitchenassistant.dayplan.queries.dto

import java.time.LocalDate

data class DayPlanCriteria(
    val userId: Int? = null,
    val archived: Boolean? = null,
    val recipeId: Int? = null,
    val since: LocalDate? = null,
    val to: LocalDate? = null,
    val name: String? = null,
    val automaticArchiving: Boolean? = null
)