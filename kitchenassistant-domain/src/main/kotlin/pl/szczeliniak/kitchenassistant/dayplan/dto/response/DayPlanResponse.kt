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
            val name: String,
            val author: String?,
            val category: String?,
            val originalRecipeId: Int?,
            val ingredientGroups: List<IngredientGroup>,
            val steps: List<Step>
        ) {
            data class IngredientGroup(
                val name: String,
                val ingredients: List<Ingredient>
            ) {
                data class Ingredient(
                    val name: String,
                    val quantity: String?,
                    val checked: Boolean
                )
            }

            data class Step(
                val description: String,
                val sequence: Int?
            )
        }
    }
}