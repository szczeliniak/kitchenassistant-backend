package pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories

import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListItem
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListItemDto

open class ShoppingListItemFactory {

    open fun create(dto: NewShoppingListItemDto): ShoppingListItem {
        return ShoppingListItem(name_ = dto.name, quantity_ = dto.quantity, unit_ = dto.unit, sequence_ = dto.sequence)
    }

}