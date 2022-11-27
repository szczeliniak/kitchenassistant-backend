package pl.szczeliniak.kitchenassistant.dayplan.queries.dto

data class SimpleRecipeDto(
    val id: Int,
    val name: String,
    val author: String?,
    val category: String?,
    val ingredientGroups: List<DayPlanIngredientGroupDto>,
)