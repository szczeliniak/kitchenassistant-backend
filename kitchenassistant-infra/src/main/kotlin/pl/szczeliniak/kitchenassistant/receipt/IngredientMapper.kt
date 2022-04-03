package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Component
import pl.szczeliniak.kitchenassistant.receipt.persistance.IngredientEntity

@Component
class IngredientMapper {

    fun toDomain(ingredientEntity: IngredientEntity): Ingredient {
        return Ingredient(
            ingredientEntity.id,
            ingredientEntity.name,
            ingredientEntity.quantity,
            ingredientEntity.deleted,
            ingredientEntity.createdAt,
            ingredientEntity.modifiedAt
        )
    }

    fun toEntity(ingredient: Ingredient): IngredientEntity {
        return IngredientEntity(
            ingredient.id,
            ingredient.name,
            ingredient.quantity,
            ingredient.deleted,
            ingredient.createdAt,
            ingredient.modifiedAt
        )
    }

}