package pl.szczeliniak.kitchenassistant.receipt

interface AuthorDao {

    fun save(author: Author): Author

    fun saveAll(authors: Set<Author>)

    fun findByName(name: String, userId: Int): Author?

    fun findAll(criteria: AuthorCriteria): Set<Author>

}