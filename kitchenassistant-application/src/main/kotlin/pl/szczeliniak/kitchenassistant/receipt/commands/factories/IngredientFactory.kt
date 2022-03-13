package pl.szczeliniak.kitchenassistant.receipt.commands.factories

import pl.szczeliniak.kitchenassistant.receipt.Ingredient
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewIngredientDto

open class IngredientFactory {

    open fun create(dto: NewIngredientDto): Ingredient {
        return Ingredient(name_ = dto.name, quantity_ = dto.quantity, unit_ = dto.unit)
    }

}