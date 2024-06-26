package pl.szczeliniak.cookbook.recipe.dto.response

data class CategoriesResponse(
    val categories: List<Category>
) {
    data class Category(
        val id: Int,
        val name: String
    )
}