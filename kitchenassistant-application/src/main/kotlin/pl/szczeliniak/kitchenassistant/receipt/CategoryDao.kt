package pl.szczeliniak.kitchenassistant.receipt

interface CategoryDao {

    fun findAll(criteria: CategoryCriteria): List<Category>

    fun save(category: Category): Category

}