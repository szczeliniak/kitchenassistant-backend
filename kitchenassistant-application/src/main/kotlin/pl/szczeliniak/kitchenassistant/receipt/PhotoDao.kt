package pl.szczeliniak.kitchenassistant.receipt

interface PhotoDao {

    fun save(photo: Photo): Photo

    fun findById(id: Int): Photo?

}