package pl.szczeliniak.kitchenassistant.recipe.queries.dto

data class RecipeDetailsDto(
        val id: Int,
        val name: String,
        val description: String?,
        val author: String?,
        val source: String?,
        val favorite: Boolean?,
        val category: CategoryDto?,
        val ingredientGroups: Set<IngredientGroupDto>,
        val steps: Set<StepDto>,
        val photo: PhotoDto?,
        val tags: Set<String>
)