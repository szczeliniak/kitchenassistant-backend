package pl.szczeliniak.cookbook.recipe.db

interface CategoryDao {
    fun findAll(userId: Int): List<Category>
    fun findById(id: Int, userId: Int): Category?
    fun save(category: Category): Category
    fun delete(category: Category)
}