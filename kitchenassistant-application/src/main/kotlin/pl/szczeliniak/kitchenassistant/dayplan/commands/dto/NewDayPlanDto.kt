package pl.szczeliniak.kitchenassistant.dayplan.commands.dto

import java.time.LocalDate
import javax.validation.constraints.Min

data class NewDayPlanDto(
    @field:Min(1) var userId: Int = 0,
    var date: LocalDate = LocalDate.now()
)