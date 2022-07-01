package pl.szczeliniak.kitchenassistant.receipt.commands.factories

import pl.szczeliniak.kitchenassistant.receipt.Ingredient
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewIngredientDto

open class IngredientFactory {

    open fun create(dto: NewIngredientDto): Ingredient {
        return Ingredient(0, dto.name, dto.quantity)
    }

}