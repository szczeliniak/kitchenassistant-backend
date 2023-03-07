package pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories

import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingList
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListDto

open class ShoppingListFactory(private val shoppingListItemFactory: ShoppingListItemFactory) {

    open fun create(dto: NewShoppingListDto): ShoppingList {
        return ShoppingList(
            userId = dto.userId,
            name = dto.name,
            description = dto.description,
            date = dto.date,
            items = dto.items.map { shoppingListItemFactory.create(it) }.toMutableSet(),
            automaticArchiving = dto.automaticArchiving
        )
    }

}