package pl.szczeliniak.kitchenassistant.dayplan.queries.dto

data class DayPlanRecipeDto(
    val name: String,
    val ingredientGroups: List<DayPlanIngredientGroupDto>,
    val author: String?
)