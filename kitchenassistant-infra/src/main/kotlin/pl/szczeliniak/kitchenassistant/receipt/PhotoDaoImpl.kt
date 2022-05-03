package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Component

@Component
class PhotoDaoImpl(
    private val photoRepository: PhotoRepository,
    private val photoMapper: PhotoMapper
) : PhotoDao {

    override fun save(photo: Photo): Photo {
        return photoMapper.toDomain(photoRepository.save(photoMapper.toEntity(photo)))
    }

    override fun saveAll(photos: Set<Photo>) {
        photos.forEach { photoRepository.save(photoMapper.toEntity(it)) }
    }

}