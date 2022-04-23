package pl.szczeliniak.kitchenassistant.receipt

interface TagDao {

    fun save(tag: Tag): Tag

    fun saveAll(tags: List<Tag>)

    fun findByName(name: String, userId: Int): Tag?

    fun findAll(criteria: TagCriteria): List<Tag>

}