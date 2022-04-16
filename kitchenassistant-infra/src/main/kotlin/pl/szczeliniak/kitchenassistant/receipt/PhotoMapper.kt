package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Component
import pl.szczeliniak.kitchenassistant.receipt.persistance.PhotoEntity

@Component
class PhotoMapper {

    fun toDomain(entity: PhotoEntity): Photo {
        return Photo(
            entity.id,
            entity.name,
            entity.deleted,
            entity.createdAt,
            entity.modifiedAt
        )
    }

    fun toEntity(photo: Photo): PhotoEntity {
        return PhotoEntity(
            photo.id,
            photo.name,
            photo.deleted,
            photo.createdAt,
            photo.modifiedAt
        )
    }

}