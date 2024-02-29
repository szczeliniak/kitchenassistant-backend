package pl.szczeliniak.kitchenassistant.recipe.db

interface CategoryDao {

    fun findAll(userId: Int): Set<Category>

    fun findById(id: Int, userId: Int): Category?

    fun save(category: Category): Category
    fun delete(category: Category)

}