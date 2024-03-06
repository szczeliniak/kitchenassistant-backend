package pl.szczeliniak.kitchenassistant.recipe.dto.response

data class CategoriesResponse(
    val categories: Set<Category>
) {
    data class Category(
        val id: Int,
        val name: String
    )
}