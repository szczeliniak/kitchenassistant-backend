package pl.szczeliniak.kitchenassistant.recipe.db

interface PhotoDao {

    fun save(photo: Photo): Photo

    fun findById(id: Int): Photo?

    fun findOrphaned(): Set<Photo>
    fun isAssigned(id: Int): Boolean

}