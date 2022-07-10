package pl.szczeliniak.kitchenassistant.dayplan.queries.dto

import java.time.LocalDate

data class DayPlanDetailsDto(
    val id: Int,
    val name: String,
    val description: String?,
    val date: LocalDate?,
    val receipts: List<SimpleReceiptDto>,
    val automaticArchiving: Boolean
)