package pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories

import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListRequest
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingList
import pl.szczeliniak.kitchenassistant.user.db.UserDao

open class ShoppingListFactory(
    private val shoppingListItemFactory: ShoppingListItemFactory,
    private val userDao: UserDao
) {

    open fun create(request: NewShoppingListRequest): ShoppingList {
        return ShoppingList(
            user = userDao.findById(request.userId) ?: throw KitchenAssistantException(ErrorCode.USER_NOT_FOUND),
            name = request.name,
            description = request.description,
            date = request.date,
            items = request.items.map { shoppingListItemFactory.create(it) }.toMutableSet(),
            automaticArchiving = request.automaticArchiving
        )
    }

}