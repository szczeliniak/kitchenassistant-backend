package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Component

@Component
class TagMapper {

    fun toDomain(entity: TagEntity): Tag {
        return Tag(
            entity.id,
            entity.name,
            entity.userId,
            entity.deleted,
            entity.createdAt,
            entity.modifiedAt
        )
    }

    fun toEntity(tag: Tag): TagEntity {
        return TagEntity(
            tag.id,
            tag.name,
            tag.userId,
            tag.deleted,
            tag.createdAt,
            tag.modifiedAt
        )
    }

}