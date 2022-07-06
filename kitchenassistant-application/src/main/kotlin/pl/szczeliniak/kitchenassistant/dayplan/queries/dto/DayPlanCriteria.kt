package pl.szczeliniak.kitchenassistant.dayplan.queries.dto

import java.time.LocalDate

data class DayPlanCriteria(
    val userId: Int? = null,
    val archived: Boolean? = null,
    val receiptId: Int? = null,
    val date: LocalDate? = null
)