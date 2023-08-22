package pl.szczeliniak.kitchenassistant.recipe.db

data class RecipeCriteria(
        val onlyFavorites: Boolean? = null,
        val userId: Int? = null,
        val categoryId: Int? = null,
        val name: String? = null,
        val tag: String? = null,
        val fileName: String? = null
)