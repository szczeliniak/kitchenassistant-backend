package pl.szczeliniak.kitchenassistant.recipe.db

interface CategoryDao {

    fun findAll(criteria: CategoryCriteria): Set<Category>

    fun findById(id: Int): Category?

    fun save(category: Category): Category

}