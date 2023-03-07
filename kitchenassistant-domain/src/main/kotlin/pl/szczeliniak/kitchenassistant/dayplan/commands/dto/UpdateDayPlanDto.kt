package pl.szczeliniak.kitchenassistant.dayplan.commands.dto

import java.time.LocalDate
import javax.validation.constraints.NotNull

data class UpdateDayPlanDto(
    @NotNull var automaticArchiving: Boolean = false,
    @NotNull var date: LocalDate = LocalDate.now()
)