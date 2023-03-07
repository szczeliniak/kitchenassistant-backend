package pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories

import pl.szczeliniak.kitchenassistant.recipe.RecipeFacade
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListItem
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListItemDto

open class ShoppingListItemFactory(private val recipeFacade: RecipeFacade) {

    open fun create(dto: NewShoppingListItemDto): ShoppingListItem {
        dto.recipeId?.let {
            recipeFacade.findById(it)
        }

        return ShoppingListItem(
            name = dto.name,
            quantity = dto.quantity,
            sequence = dto.sequence,
            recipeId = dto.recipeId
        )
    }

}