package pl.szczeliniak.kitchenassistant.shoppinglist

import org.springframework.stereotype.Component

@Component
class ShoppingListItemMapper {

    fun toDomain(shoppingListItemEntity: ShoppingListItemEntity): ShoppingListItem {
        return ShoppingListItem(
            shoppingListItemEntity.id,
            shoppingListItemEntity.name,
            shoppingListItemEntity.quantity,
            shoppingListItemEntity.sequence,
            shoppingListItemEntity.deleted,
            shoppingListItemEntity.completed,
            shoppingListItemEntity.createdAt,
            shoppingListItemEntity.modifiedAt
        )
    }

    fun toEntity(shoppingListItem: ShoppingListItem): ShoppingListItemEntity {
        return ShoppingListItemEntity(
            shoppingListItem.id,
            shoppingListItem.name,
            shoppingListItem.quantity,
            shoppingListItem.sequence,
            shoppingListItem.deleted,
            shoppingListItem.completed,
            shoppingListItem.createdAt,
            shoppingListItem.modifiedAt
        )
    }

}