package pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories

import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingList
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListDto

open class ShoppingListFactory(private val shoppingListItemFactory: ShoppingListItemFactory) {

    open fun create(dto: NewShoppingListDto): ShoppingList {
        return ShoppingList(
            userId_ = dto.userId,
            name_ = dto.name,
            description_ = dto.description,
            date_ = dto.date,
            items_ = dto.items.map { shoppingListItemFactory.create(it) }.toMutableList()
        )
    }

}