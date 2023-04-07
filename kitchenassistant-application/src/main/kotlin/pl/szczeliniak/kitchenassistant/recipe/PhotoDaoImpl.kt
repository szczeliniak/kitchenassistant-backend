package pl.szczeliniak.kitchenassistant.recipe

import pl.szczeliniak.kitchenassistant.recipe.db.Photo
import pl.szczeliniak.kitchenassistant.recipe.db.PhotoDao
import pl.szczeliniak.kitchenassistant.recipe.db.PhotoRepository

class PhotoDaoImpl(private val photoRepository: PhotoRepository) : PhotoDao {

    override fun save(photo: Photo): Photo {
        return photoRepository.save(photo)
    }

    override fun findById(id: Int): Photo? {
        return photoRepository.findById(id).orElse(null)
    }

    override fun findOrphaned(): Set<Photo> {
        return photoRepository.findOrphaned()
    }

    override fun isAssigned(id: Int): Boolean {
        return photoRepository.findAssigned().any()
    }

}