package pl.szczeliniak.kitchenassistant.shoppinglist

import org.springframework.stereotype.Component

@Component
class ShoppingListMapper(private val shoppingListItemMapper: ShoppingListItemMapper) {

    fun toDomain(shoppingListEntity: ShoppingListEntity): ShoppingList {
        return ShoppingList(
            shoppingListEntity.id,
            shoppingListEntity.userId,
            shoppingListEntity.name,
            shoppingListEntity.description,
            shoppingListEntity.date,
            shoppingListEntity.items.map { shoppingListItemMapper.toDomain(it) }.toMutableSet(),
            shoppingListEntity.deleted,
            shoppingListEntity.archived,
            shoppingListEntity.createdAt,
            shoppingListEntity.modifiedAt
        )
    }

    fun toEntity(shoppingList: ShoppingList): ShoppingListEntity {
        return ShoppingListEntity(
            shoppingList.id,
            shoppingList.userId,
            shoppingList.name,
            shoppingList.description,
            shoppingList.date,
            shoppingList.items.map { shoppingListItemMapper.toEntity(it) }.toMutableSet(),
            shoppingList.deleted,
            shoppingList.archived,
            shoppingList.createdAt,
            shoppingList.modifiedAt
        )
    }

}