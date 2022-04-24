package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Component
import pl.szczeliniak.kitchenassistant.receipt.persistance.PhotoRepository

@Component
class PhotoDaoImpl(
    private val photoRepository: PhotoRepository,
    private val photoMapper: PhotoMapper
) : PhotoDao {

    override fun save(photo: Photo): Photo {
        return photoMapper.toDomain(photoRepository.save(photoMapper.toEntity(photo)))
    }

    override fun findById(id: Int): Photo? {
        return photoRepository.findById(id)?.let { photoMapper.toDomain(it) }
    }

}