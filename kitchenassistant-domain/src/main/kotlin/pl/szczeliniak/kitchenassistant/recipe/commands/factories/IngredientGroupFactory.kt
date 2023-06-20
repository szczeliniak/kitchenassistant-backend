package pl.szczeliniak.kitchenassistant.recipe.commands.factories

import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewIngredientGroupRequest
import pl.szczeliniak.kitchenassistant.recipe.db.IngredientGroup

open class IngredientGroupFactory(
    private val ingredientFactory: IngredientFactory
) {

    open fun create(request: NewIngredientGroupRequest): IngredientGroup {
        return IngredientGroup(
            0,
            request.name,
            request.ingredients.map { ingredientFactory.create(it.name, it.quantity) }.toMutableSet()
        )
    }

}