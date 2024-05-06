package pl.szczeliniak.cookbook.dayplan.dto.request

import java.time.LocalDate
import javax.validation.constraints.NotNull

data class UpdateDayPlanRequest(
    @NotNull var date: LocalDate = LocalDate.now()
)