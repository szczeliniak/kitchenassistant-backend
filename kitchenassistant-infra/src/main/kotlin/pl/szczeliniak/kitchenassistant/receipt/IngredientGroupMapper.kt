package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Component

@Component
class IngredientGroupMapper(
    private val ingredientMapper: IngredientMapper
) {

    fun toDomain(ingredientGroupEntity: IngredientGroupEntity): IngredientGroup {
        return IngredientGroup(
            ingredientGroupEntity.id,
            ingredientGroupEntity.name,
            ingredientGroupEntity.ingredients.map { ingredientMapper.toDomain(it) }.toMutableSet(),
            ingredientGroupEntity.deleted,
            ingredientGroupEntity.createdAt,
            ingredientGroupEntity.modifiedAt
        )
    }

    fun toEntity(ingredientGroup: IngredientGroup): IngredientGroupEntity {
        return IngredientGroupEntity(
            ingredientGroup.id,
            ingredientGroup.name,
            ingredientGroup.ingredients.map { ingredientMapper.toEntity(it) }.toMutableSet(),
            ingredientGroup.deleted,
            ingredientGroup.createdAt,
            ingredientGroup.modifiedAt
        )
    }

}