package pl.szczeliniak.kitchenassistant.recipe.db

interface RecipeDao {
    fun findById(id: Int, archived: Boolean?, userId: Int): Recipe?
    fun findAll(criteria: RecipeCriteria, userId: Int, offset: Int? = null, limit: Int? = null): List<Recipe>
    fun save(recipe: Recipe): Recipe
    fun save(recipes: List<Recipe>)
    fun count(criteria: RecipeCriteria, userId: Int): Long
}