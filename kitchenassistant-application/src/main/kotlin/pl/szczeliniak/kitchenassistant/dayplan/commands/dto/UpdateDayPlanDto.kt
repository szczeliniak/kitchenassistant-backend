package pl.szczeliniak.kitchenassistant.dayplan.commands.dto

import org.hibernate.validator.constraints.Length
import java.time.LocalDate

data class UpdateDayPlanDto(
    @field:Length(min = 1, max = 100) var name: String = "",
    @field:Length(min = 1, max = 1000) var description: String? = null,
    var date: LocalDate? = null
)