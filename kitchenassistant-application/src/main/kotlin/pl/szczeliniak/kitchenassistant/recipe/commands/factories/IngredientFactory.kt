package pl.szczeliniak.kitchenassistant.recipe.commands.factories

import pl.szczeliniak.kitchenassistant.recipe.Ingredient
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewIngredientDto

open class IngredientFactory {

    open fun create(dto: NewIngredientDto): Ingredient {
        return Ingredient(0, dto.name, dto.quantity)
    }

}