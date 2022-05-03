package pl.szczeliniak.kitchenassistant.receipt

interface PhotoDao {

    fun save(photo: Photo): Photo

    fun saveAll(photos: Set<Photo>)

}