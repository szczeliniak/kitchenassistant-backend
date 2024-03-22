package pl.szczeliniak.kitchenassistant.dayplan.dto.response

import java.time.LocalDate

data class DayPlanResponse(
    val dayPlan: DayPlan
) {
    data class DayPlan(
        val date: LocalDate,
        val recipes: List<Recipe>
    ) {
        data class Recipe(
            val id: Int,
            val name: String,
            val source: String?,
            val originalRecipeId: Int,
            val ingredientGroups: List<IngredientGroup>
        ) {
            data class IngredientGroup(
                val id: Int,
                val name: String?,
                val ingredients: List<Ingredient>
            ) {
                data class Ingredient(
                    val id: Int,
                    val name: String,
                    val quantity: String?,
                    val checked: Boolean
                )
            }
        }
    }
}