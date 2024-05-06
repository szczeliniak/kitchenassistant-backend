package pl.szczeliniak.cookbook.dayplan.dto.request

import java.time.LocalDate
import javax.validation.constraints.NotNull

data class AddRecipeToDayPlanRequest(
    @NotNull var date: LocalDate = LocalDate.now(),
    @NotNull var recipeId: Int = 0
)