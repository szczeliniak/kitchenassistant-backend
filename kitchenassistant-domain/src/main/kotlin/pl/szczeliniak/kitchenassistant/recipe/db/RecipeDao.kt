package pl.szczeliniak.kitchenassistant.recipe.db

interface RecipeDao {

    fun findById(id: Int): Recipe?

    fun findAll(criteria: RecipeCriteria, offset: Int? = null, limit: Int? = null): Set<Recipe>

    fun save(recipe: Recipe): Recipe

    fun save(recipes: Set<Recipe>)

    fun count(criteria: RecipeCriteria): Long

}