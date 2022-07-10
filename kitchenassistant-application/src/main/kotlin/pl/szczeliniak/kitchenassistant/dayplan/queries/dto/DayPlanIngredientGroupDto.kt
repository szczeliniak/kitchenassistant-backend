package pl.szczeliniak.kitchenassistant.dayplan.queries.dto

data class DayPlanIngredientGroupDto(
    val name: String,
    val ingredients: List<DayPlanIngredientDto>
)