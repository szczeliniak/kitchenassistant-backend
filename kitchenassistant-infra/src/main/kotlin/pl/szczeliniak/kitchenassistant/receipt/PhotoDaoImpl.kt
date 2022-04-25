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

}