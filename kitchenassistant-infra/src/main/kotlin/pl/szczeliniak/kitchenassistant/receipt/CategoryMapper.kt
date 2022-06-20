package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Component

@Component
class CategoryMapper {

    fun toDomain(entity: CategoryEntity): Category {
        return Category(
            entity.id,
            entity.name,
            entity.userId,
            entity.sequence,
            entity.deleted,
            entity.createdAt,
            entity.modifiedAt
        )
    }

    fun toEntity(category: Category): CategoryEntity {
        return CategoryEntity(
            category.id,
            category.name,
            category.userId,
            category.sequence,
            category.deleted,
            category.createdAt,
            category.modifiedAt
        )
    }

}