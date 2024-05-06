package pl.szczeliniak.cookbook.recipe.db

interface TagDao {

    fun save(tag: Tag): Tag
    fun findById(id: Int, userId: Int): Tag?
    fun findByName(name: String, userId: Int): Tag?
    fun findAll(criteria: TagCriteria, userId: Int): List<Tag>

}