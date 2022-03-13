package pl.szczeliniak.kitchenassistant.shoppinglist

import org.springframework.stereotype.Component
import pl.szczeliniak.kitchenassistant.shoppinglist.persistance.ShoppingListEntity

@Component
class ShoppingListMapper(private val shoppingListItemMapper: ShoppingListItemMapper) {

    fun toDomain(shoppingListEntity: ShoppingListEntity): ShoppingList {
        return ShoppingList(
            shoppingListEntity.id,
            shoppingListEntity.userId,
            shoppingListEntity.title,
            shoppingListEntity.description,
            shoppingListEntity.items.map { shoppingListItemMapper.toDomain(it) },
            shoppingListEntity.deleted,
            shoppingListEntity.createdAt,
            shoppingListEntity.modifiedAt
        )
    }

    fun toEntity(shoppingList: ShoppingList): ShoppingListEntity {
        return ShoppingListEntity(
            shoppingList.id,
            shoppingList.userId,
            shoppingList.title,
            shoppingList.description,
            shoppingList.items.map { shoppingListItemMapper.toEntity(it) },
            shoppingList.deleted,
            shoppingList.createdAt,
            shoppingList.modifiedAt
        )
    }

}