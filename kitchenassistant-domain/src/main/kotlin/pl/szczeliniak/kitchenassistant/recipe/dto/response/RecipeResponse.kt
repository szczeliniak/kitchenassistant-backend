package pl.szczeliniak.kitchenassistant.recipe.dto.response

data class RecipeResponse(
    val recipe: Recipe
) {
    data class Recipe(
        val id: Int,
        val name: String,
        val description: String?,
        val author: String?,
        val source: String?,
        val favorite: Boolean,
        val category: Category?,
        val ingredientGroups: Set<IngredientGroup>,
        val stepGroups: Set<StepGroup>,
        val tags: Set<String>
    ) {
        data class IngredientGroup(
            val id: Int,
            val name: String?,
            val ingredients: Set<Ingredient>
        ) {
            data class Ingredient(
                val id: Int,
                val name: String,
                val quantity: String?
            )
        }

        data class StepGroup(
            val id: Int,
            val name: String?,
            val steps: Set<Step>
        ) {
            data class Step(
                val id: Int,
                val description: String
            )
        }

        data class Category(
            val id: Int,
            val name: String
        )
    }
}