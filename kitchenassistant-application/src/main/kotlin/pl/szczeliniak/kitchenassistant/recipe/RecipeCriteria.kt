package pl.szczeliniak.kitchenassistant.recipe

data class RecipeCriteria(
    val userId: Int? = null,
    val categoryId: Int? = null,
    val name: String? = null,
    val tag: String? = null
)