package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Component
import pl.szczeliniak.kitchenassistant.receipt.persistance.FileEntity

@Component
class FileMapper {

    fun toDomain(entity: FileEntity): File {
        return File(
            entity.id,
            entity.name,
            entity.deleted,
            entity.createdAt,
            entity.modifiedAt
        )
    }

    fun toEntity(file: File): FileEntity {
        return FileEntity(
            file.id,
            file.name,
            file.deleted,
            file.createdAt,
            file.modifiedAt
        )
    }

}