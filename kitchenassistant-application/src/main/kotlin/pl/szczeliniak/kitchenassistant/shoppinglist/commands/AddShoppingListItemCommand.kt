package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListItemDto
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories.ShoppingListItemFactory

class AddShoppingListItemCommand(
    private val shoppingListDao: ShoppingListDao,
    private val shoppingListItemFactory: ShoppingListItemFactory
) {

    fun execute(shoppingListId: Int, dto: NewShoppingListItemDto): SuccessResponse {
        val shoppingList =
            shoppingListDao.findById(shoppingListId) ?: throw NotFoundException("Shopping list not found")

        val item = shoppingListItemFactory.create(dto)
        shoppingList.addItem(item)
        shoppingListDao.save(shoppingList)

        return SuccessResponse(item.id)
    }

}