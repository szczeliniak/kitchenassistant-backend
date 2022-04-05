package pl.szczeliniak.kitchenassistant.receipt

interface CategoryDao {

    fun findAll(criteria: CategoryCriteria): List<Category>

    fun findById(id: Int): Category?

    fun save(category: Category): Category

}