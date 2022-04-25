package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Component

@Component
class PhotoMapper {

    fun toDomain(entity: PhotoEntity): Photo {
        return Photo(
            entity.id,
            entity.fileId,
            entity.deleted,
            entity.createdAt,
            entity.modifiedAt
        )
    }

    fun toEntity(file: Photo): PhotoEntity {
        return PhotoEntity(
            file.id,
            file.fileId,
            file.deleted,
            file.createdAt,
            file.modifiedAt
        )
    }

}