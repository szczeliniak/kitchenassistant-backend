package pl.szczeliniak.kitchenassistant.recipe.db

interface AuthorDao {

    fun save(author: Author): Author

    fun saveAll(authors: Set<Author>)

    fun findById(id: Int, userId: Int): Author?

    fun findByName(name: String, userId: Int): Author?

    fun findAll(criteria: AuthorCriteria, userId: Int): Set<Author>

}