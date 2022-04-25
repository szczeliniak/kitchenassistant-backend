package pl.szczeliniak.kitchenassistant.shoppinglist

import org.springframework.stereotype.Component

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