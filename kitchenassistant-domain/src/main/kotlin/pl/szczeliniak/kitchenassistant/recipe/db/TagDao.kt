package pl.szczeliniak.kitchenassistant.recipe.db

interface TagDao {

    fun save(tag: Tag): Tag
    fun saveAll(tags: Set<Tag>)
    fun findById(id: Int, userId: Int): Tag?
    fun findByName(name: String, userId: Int): Tag?
    fun findAll(criteria: TagCriteria, userId: Int): Set<Tag>

}