package pl.szczeliniak.kitchenassistant.recipe.dto.response

data class CategoriesResponse(
    val categories: Set<CategoryDto>
) {
    data class CategoryDto(
        val id: Int,
        val name: String,
        var sequence: Int?
    )
}