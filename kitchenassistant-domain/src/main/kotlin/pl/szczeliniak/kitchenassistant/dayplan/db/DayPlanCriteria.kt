package pl.szczeliniak.kitchenassistant.dayplan.db

import java.time.LocalDate

data class DayPlanCriteria(
    val recipeId: Int? = null,
    val since: LocalDate? = null,
    val to: LocalDate? = null
)