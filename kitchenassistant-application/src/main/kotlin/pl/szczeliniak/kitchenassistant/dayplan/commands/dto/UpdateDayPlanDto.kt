package pl.szczeliniak.kitchenassistant.dayplan.commands.dto

import java.time.LocalDate

data class UpdateDayPlanDto(
    var date: LocalDate = LocalDate.now()
)