package pl.szczeliniak.kitchenassistant.recipe.dto.response

import pl.szczeliniak.kitchenassistant.shared.dtos.Page

data class RecipesResponse(
    val recipes: Page<RecipeDto>,
) {
    data class RecipeDto(
        val id: Int,
        val name: String,
        val author: String?,
        val favorite: Boolean,
        val category: CategoryDto?,
        val tags: Set<String>,
        val photoName: String?
    ) {
        data class CategoryDto(
            val id: Int,
            val name: String,
            var sequence: Int?
        )
    }
}