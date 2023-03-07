package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListItemDao
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListItemDto
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories.ShoppingListItemFactory

class AddShoppingListItemCommand(
        private val shoppingListDao: ShoppingListDao,
        private val shoppingListItemDao: ShoppingListItemDao,
        private val shoppingListItemFactory: ShoppingListItemFactory
) {

    fun execute(shoppingListId: Int, dto: NewShoppingListItemDto): SuccessResponse {
        val shoppingList =
            shoppingListDao.findById(shoppingListId)
                ?: throw KitchenAssistantException(ErrorCode.SHOPPING_LIST_NOT_FOUND)

        val item = shoppingListItemDao.save(shoppingListItemFactory.create(dto))
        shoppingList.items.add(item)
        shoppingListDao.save(shoppingList)
        return SuccessResponse(item.id)
    }

}