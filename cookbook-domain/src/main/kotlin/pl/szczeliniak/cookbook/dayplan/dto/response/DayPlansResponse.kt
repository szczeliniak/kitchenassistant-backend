package pl.szczeliniak.cookbook.dayplan.dto.response

import pl.szczeliniak.cookbook.shared.dtos.Page
import java.time.LocalDate

data class DayPlansResponse(
    val dayPlans: Page<DayPlan>
) {
    data class DayPlan(
        val date: LocalDate,
        val recipes: List<Recipe>
    ) {
        data class Recipe(val id: Int, val name: String)
    }
}