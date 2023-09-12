package pl.szczeliniak.kitchenassistant.dayplan.dto.response

import java.time.LocalDate

data class DayPlanResponse(
    val dayPlan: DayPlanDto
) {
    data class DayPlanDto(
        val id: Int,
        val date: LocalDate,
        val recipes: List<RecipeDto>
    ) {
        data class RecipeDto(
            val id: Int,
            val name: String,
            val author: String?,
            val category: String?,
            val ingredientGroups: List<IngredientGroupDto>,
        ) {
            data class IngredientGroupDto(
                val id: Int,
                val name: String,
                val ingredients: List<IngredientDto>
            ) {
                data class IngredientDto(
                    val id: Int,
                    val name: String,
                    val quantity: String?,
                    val checked: Boolean
                )
            }
        }
    }
}