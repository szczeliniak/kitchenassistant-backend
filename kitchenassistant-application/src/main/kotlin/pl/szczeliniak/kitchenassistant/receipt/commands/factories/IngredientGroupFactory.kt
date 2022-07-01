package pl.szczeliniak.kitchenassistant.receipt.commands.factories

import pl.szczeliniak.kitchenassistant.receipt.IngredientGroup
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewIngredientGroupDto

open class IngredientGroupFactory(
    private val ingredientFactory: IngredientFactory
) {

    open fun create(dto: NewIngredientGroupDto): IngredientGroup {
        return IngredientGroup(0, dto.name, dto.ingredients.map { ingredientFactory.create(it) }.toMutableSet())
    }

}