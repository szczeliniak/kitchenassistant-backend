package pl.szczeliniak.kitchenassistant.dayplan.queries.dto

data class DayPlanRecipeDto(
    val id: Int,
    val name: String,
    val ingredientGroups: List<DayPlanIngredientGroupDto>,
    val author: String?
)