package pl.szczeliniak.kitchenassistant.recipe.dto.response

data class RecipeResponse(
    val recipe: RecipeDto
) {
    data class RecipeDto(
        val id: Int,
        val name: String,
        val description: String?,
        val author: String?,
        val source: String?,
        val favorite: Boolean,
        val category: CategoryDto?,
        val ingredientGroups: Set<IngredientGroupDto>,
        val steps: Set<StepDto>,
        val photoName: String?,
        val tags: Set<String>
    ) {
        data class IngredientGroupDto(
            val id: Int,
            val name: String,
            val ingredients: Set<IngredientDto>
        ) {
            data class IngredientDto(
                val id: Int,
                val name: String,
                val quantity: String?
            )
        }

        data class CategoryDto(
            val id: Int,
            val name: String,
            var sequence: Int?
        )

        data class StepDto(
            val id: Int,
            val description: String,
            val photoName: String?,
            val sequence: Int?
        )
    }
}