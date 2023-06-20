package pl.szczeliniak.kitchenassistant.dayplan.commands.dto

import java.time.LocalDate
import javax.validation.constraints.NotNull

data class UpdateDayPlanRequest(
    @NotNull var automaticArchiving: Boolean = false,
    @NotNull var date: LocalDate = LocalDate.now()
)