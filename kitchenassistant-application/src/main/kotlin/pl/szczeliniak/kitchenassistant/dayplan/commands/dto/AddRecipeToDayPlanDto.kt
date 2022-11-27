package pl.szczeliniak.kitchenassistant.dayplan.commands.dto

import java.time.LocalDate
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class AddRecipeToDayPlanDto(
    @field:Min(1) var userId: Int = 0,
    @NotNull var date: LocalDate = LocalDate.now(),
    var automaticArchiving: Boolean = false
)