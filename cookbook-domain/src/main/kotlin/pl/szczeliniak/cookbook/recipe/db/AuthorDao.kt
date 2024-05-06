package pl.szczeliniak.cookbook.recipe.db

interface AuthorDao {
    fun save(author: Author): Author
    fun saveAll(authors: List<Author>)
    fun findById(id: Int, userId: Int): Author?
    fun findByName(name: String, userId: Int): Author?
    fun findAll(criteria: AuthorCriteria, userId: Int): List<Author>
}