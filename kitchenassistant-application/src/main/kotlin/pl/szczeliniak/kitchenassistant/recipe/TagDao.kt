package pl.szczeliniak.kitchenassistant.recipe

interface TagDao {

    fun save(tag: Tag): Tag
    fun saveAll(tags: Set<Tag>)
    fun findById(id: Int): Tag?
    fun findByName(name: String, userId: Int): Tag?
    fun findAll(criteria: TagCriteria): Set<Tag>

}