package pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories

import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListDto
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingList
import pl.szczeliniak.kitchenassistant.user.db.UserDao

open class ShoppingListFactory(
    private val shoppingListItemFactory: ShoppingListItemFactory,
    private val userDao: UserDao
) {

    open fun create(dto: NewShoppingListDto): ShoppingList {
        return ShoppingList(
            user = userDao.findById(dto.userId) ?: throw KitchenAssistantException(ErrorCode.USER_NOT_FOUND),
            name = dto.name,
            description = dto.description,
            date = dto.date,
            items = dto.items.map { shoppingListItemFactory.create(it) }.toMutableSet(),
            automaticArchiving = dto.automaticArchiving
        )
    }

}