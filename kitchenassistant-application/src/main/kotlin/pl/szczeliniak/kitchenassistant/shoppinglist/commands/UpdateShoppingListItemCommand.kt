package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListItemDao
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.UpdateShoppingListItemDto

class UpdateShoppingListItemCommand(
    private val shoppingListDao: ShoppingListDao,
    private val shoppingListItemDao: ShoppingListItemDao
) {

    fun execute(shoppingListId: Int, ingredientId: Int, dto: UpdateShoppingListItemDto): SuccessResponse {
        val shoppingList =
            shoppingListDao.findById(shoppingListId) ?: throw NotFoundException("Shopping list not found")

        val item =
            shoppingList.items.firstOrNull { it.id == ingredientId }
                ?: throw NotFoundException("Shopping list item not found")

        item.update(dto.name, dto.quantity, dto.sequence)

        return SuccessResponse(shoppingListItemDao.save(item).id)
    }

}