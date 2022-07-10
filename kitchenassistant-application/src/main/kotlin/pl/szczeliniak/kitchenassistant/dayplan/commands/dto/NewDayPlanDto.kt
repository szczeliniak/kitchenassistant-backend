package pl.szczeliniak.kitchenassistant.dayplan.commands.dto

import org.hibernate.validator.constraints.Length
import java.time.LocalDate
import javax.validation.constraints.Min

data class NewDayPlanDto(
    @field:Length(min = 1, max = 100) var name: String = "",
    @field:Length(min = 1, max = 1000) var description: String? = null,
    @field:Min(1) var userId: Int = 0,
    var date: LocalDate? = null
)