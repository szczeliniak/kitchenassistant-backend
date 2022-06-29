package pl.szczeliniak.kitchenassistant.receipt.queries

import pl.szczeliniak.kitchenassistant.receipt.*
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.*

open class ReceiptConverter {

    open fun map(receipt: Receipt): ReceiptDto {
        return ReceiptDto(
            receipt.id,
            receipt.name,
            receipt.author?.name,
            receipt.favorite,
            receipt.category?.let { map(it) },
            receipt.tags.map { it.name }.toSet()
        )
    }

    open fun mapDetails(receipt: Receipt): ReceiptDetailsDto {
        return ReceiptDetailsDto(
            receipt.id,
            receipt.name,
            receipt.description,
            receipt.author?.name,
            receipt.source,
            receipt.favorite,
            receipt.category?.let { map(it) },
            receipt.ingredientGroups.map { map(it) }.toSet(),
            receipt.steps.map { map(it) }.toSet(),
            receipt.photos.map { map(it) }.toSet(),
            receipt.tags.map { it.name }.toSet()
        )
    }

    open fun map(category: Category): CategoryDto {
        return CategoryDto(
            category.id,
            category.name,
            category.sequence
        )
    }

    private fun map(ingredient: Ingredient): IngredientDto {
        return IngredientDto(
            ingredient.id,
            ingredient.name,
            ingredient.quantity
        )
    }

    private fun map(photo: Photo): PhotoDto {
        return PhotoDto(photo.id, photo.name)
    }

    private fun map(step: Step): StepDto {
        return StepDto(
            step.id,
            step.name,
            step.description,
            step.sequence
        )
    }

    private fun map(ingredientGroup: IngredientGroup): IngredientGroupDto {
        return IngredientGroupDto(
            ingredientGroup.id,
            ingredientGroup.name,
            ingredientGroup.ingredients.map { map(it) }.toSet()
        )
    }

}