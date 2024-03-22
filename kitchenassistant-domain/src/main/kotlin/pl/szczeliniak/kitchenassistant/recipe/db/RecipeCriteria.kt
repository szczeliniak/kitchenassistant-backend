package pl.szczeliniak.kitchenassistant.recipe.db

data class RecipeCriteria(
        val favorite: Boolean? = null,
        val categoryId: Int? = null,
        val search: String? = null,
        val tag: String? = null,
        val archived: Boolean? = null
)