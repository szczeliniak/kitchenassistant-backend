package pl.szczeliniak.kitchenassistant.dayplan.dto.response

import pl.szczeliniak.kitchenassistant.shared.dtos.Page
import java.time.LocalDate

data class DayPlansResponse(
    val dayPlans: Page<DayPlanDto>
) {
    data class DayPlanDto(
        val id: Int,
        val date: LocalDate
    )
}