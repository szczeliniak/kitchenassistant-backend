package pl.szczeliniak.kitchenassistant.recipe

data class RecipeCriteria(
        val onlyFavorites: Boolean,
        val userId: Int? = null,
        val categoryId: Int? = null,
        val name: String? = null,
        val tag: String? = null
)