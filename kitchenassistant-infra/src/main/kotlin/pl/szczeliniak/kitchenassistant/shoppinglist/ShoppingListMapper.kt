package pl.szczeliniak.kitchenassistant.shoppinglist

import org.springframework.stereotype.Component
import pl.szczeliniak.kitchenassistant.shoppinglist.persistance.ShoppingListEntity

@Component
class ShoppingListMapper(private val shoppingListItemMapper: ShoppingListItemMapper) {

    fun toDomain(shoppingListEntity: ShoppingListEntity): ShoppingList {
        return ShoppingList(
            shoppingListEntity.id,
            shoppingListEntity.userId,
            shoppingListEntity.name,
            shoppingListEntity.description,
            shoppingListEntity.date,
            shoppingListEntity.items.map { shoppingListItemMapper.toDomain(it) }.toMutableList(),
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
            shoppingList.items.map { shoppingListItemMapper.toEntity(it) }.toMutableList(),
            shoppingList.deleted,
            shoppingList.archived,
            shoppingList.createdAt,
            shoppingList.modifiedAt
        )
    }

}