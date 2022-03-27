package pl.szczeliniak.kitchenassistant.shoppinglist

import org.springframework.stereotype.Component
import pl.szczeliniak.kitchenassistant.shoppinglist.persistance.ShoppingListItemRepository

@Component
class ShoppingListItemDaoImpl(
    private val shoppingListItemRepository: ShoppingListItemRepository,
    private val shoppingListItemMapper: ShoppingListItemMapper
) : ShoppingListItemDao {

    override fun save(shoppingListItem: ShoppingListItem): ShoppingListItem {
        return shoppingListItemMapper.toDomain(
            shoppingListItemRepository.save(shoppingListItemMapper.toEntity(shoppingListItem))
        )
    }

}