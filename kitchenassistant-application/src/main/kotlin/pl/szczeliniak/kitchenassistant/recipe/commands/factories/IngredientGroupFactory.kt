package pl.szczeliniak.kitchenassistant.recipe.commands.factories

import pl.szczeliniak.kitchenassistant.recipe.IngredientGroup
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewIngredientGroupDto

open class IngredientGroupFactory(
    private val ingredientFactory: IngredientFactory
) {

    open fun create(dto: NewIngredientGroupDto): IngredientGroup {
        return IngredientGroup(0, dto.name, dto.ingredients.map { ingredientFactory.create(it) }.toMutableSet())
    }

}