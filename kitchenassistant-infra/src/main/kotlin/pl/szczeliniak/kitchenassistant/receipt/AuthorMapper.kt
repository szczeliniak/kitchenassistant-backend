package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Component

@Component
class AuthorMapper {

    fun toDomain(entity: AuthorEntity): Author {
        return Author(
            entity.id,
            entity.name,
            entity.userId,
            entity.createdAt,
            entity.modifiedAt
        )
    }

    fun toEntity(author: Author): AuthorEntity {
        return AuthorEntity(
            author.id,
            author.name,
            author.userId,
            author.createdAt,
            author.modifiedAt
        )
    }

}