package pl.szczeliniak.kitchenassistant.recipe.commands.factories

import pl.szczeliniak.kitchenassistant.recipe.db.Ingredient

open class IngredientFactory {

    open fun create(name: String, quantity: String?): Ingredient {
        return Ingredient(0, name, quantity)
    }

}