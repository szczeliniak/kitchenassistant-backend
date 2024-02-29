package pl.szczeliniak.kitchenassistant.recipe.db

interface RecipeDao {

    fun findById(id: Int, userId: Int): Recipe?

    fun findAll(criteria: RecipeCriteria, userId: Int, offset: Int? = null, limit: Int? = null): Set<Recipe>

    fun save(recipe: Recipe): Recipe

    fun save(recipes: Set<Recipe>)
    fun count(criteria: RecipeCriteria, userId: Int): Long
    fun delete(recipe: Recipe)

}