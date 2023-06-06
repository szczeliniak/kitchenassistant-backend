package pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories

import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListItemDto
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListItem

open class ShoppingListItemFactory(private val recipeDao: RecipeDao) {

    open fun create(dto: NewShoppingListItemDto): ShoppingListItem {
        return ShoppingListItem(
            name = dto.name,
            quantity = dto.quantity,
            sequence = dto.sequence,
            recipe = dto.recipeId?.let {
                recipeDao.findById(it) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
            }
        )
    }

}