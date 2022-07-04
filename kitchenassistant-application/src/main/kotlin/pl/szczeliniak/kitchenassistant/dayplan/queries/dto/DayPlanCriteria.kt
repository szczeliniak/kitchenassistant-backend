package pl.szczeliniak.kitchenassistant.dayplan.queries.dto

data class DayPlanCriteria(
    val userId: Int? = null,
    val archived: Boolean? = null,
    val receiptId: Int? = null
)