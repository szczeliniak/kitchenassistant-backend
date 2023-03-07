package pl.szczeliniak.kitchenassistant.recipe.db

interface PhotoDao {

    fun save(photo: Photo): Photo

    fun saveAll(photos: Set<Photo>)

    fun findById(id: Int): Photo?

}