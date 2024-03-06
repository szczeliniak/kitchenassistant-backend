package pl.szczeliniak.kitchenassistant.recipe.dto.response

import pl.szczeliniak.kitchenassistant.shared.dtos.Page

data class RecipesResponse(
    val recipes: Page<Recipe>,
) {
    data class Recipe(
        val id: Int,
        val name: String,
        val author: String?,
        val favorite: Boolean,
        val category: Category?,
        val tags: Set<String>
    ) {
        data class Category(
            val id: Int,
            val name: String
        )
    }
}