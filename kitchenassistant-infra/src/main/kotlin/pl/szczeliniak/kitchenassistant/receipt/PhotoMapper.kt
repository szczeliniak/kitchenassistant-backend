package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Component

@Component
class PhotoMapper {

    fun toDomain(entity: PhotoEntity): Photo {
        return Photo(
            entity.id,
            entity.name,
            entity.userId,
            entity.deleted,
            entity.createdAt,
            entity.modifiedAt
        )
    }

    fun toEntity(photo: Photo): PhotoEntity {
        return PhotoEntity(
            photo.id,
            photo.name,
            photo.userId,
            photo.deleted,
            photo.createdAt,
            photo.modifiedAt
        )
    }

}