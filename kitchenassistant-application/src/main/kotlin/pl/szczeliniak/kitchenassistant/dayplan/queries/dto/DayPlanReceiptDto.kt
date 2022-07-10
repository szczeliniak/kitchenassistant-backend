package pl.szczeliniak.kitchenassistant.dayplan.queries.dto

data class DayPlanReceiptDto(
    val name: String,
    val ingredientGroups: List<DayPlanIngredientGroupDto>,
    val author: String?
)