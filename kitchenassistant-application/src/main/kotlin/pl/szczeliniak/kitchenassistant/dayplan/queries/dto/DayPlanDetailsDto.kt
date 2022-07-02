package pl.szczeliniak.kitchenassistant.dayplan.queries.dto

import java.time.LocalDate

data class DayPlanDetailsDto(
    private val id: Int,
    private val date: LocalDate,
    private val receipts: List<SimpleReceiptDto>
)