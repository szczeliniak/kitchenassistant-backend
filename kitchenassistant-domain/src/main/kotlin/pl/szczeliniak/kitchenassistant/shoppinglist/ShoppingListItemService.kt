package pl.szczeliniak.kitchenassistant.shoppinglist

import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListItem
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListItemDao
import pl.szczeliniak.kitchenassistant.shoppinglist.dto.request.NewShoppingListItemRequest
import pl.szczeliniak.kitchenassistant.shoppinglist.dto.request.UpdateShoppingListItemRequest

open class ShoppingListItemService(
    private val shoppingListDao: ShoppingListDao,
    private val shoppingListItemDao: ShoppingListItemDao,
    private val recipeDao: RecipeDao,
) {

    fun add(id: Int, request: NewShoppingListItemRequest): SuccessResponse {
        val shoppingList =
            shoppingListDao.findById(id)
                ?: throw KitchenAssistantException(ErrorCode.SHOPPING_LIST_NOT_FOUND)

        val item = shoppingListItemDao.save(createShoppingListItem(request))
        shoppingList.items.add(item)
        shoppingListDao.save(shoppingList)
        return SuccessResponse(item.id)
    }

    private fun createShoppingListItem(request: NewShoppingListItemRequest): ShoppingListItem {
        return ShoppingListItem(
            name = request.name,
            quantity = request.quantity,
            sequence = request.sequence,
            recipe = request.recipeId?.let {
                recipeDao.findById(it) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
            }
        )
    }

    fun update(id: Int, itemId: Int, request: UpdateShoppingListItemRequest): SuccessResponse {
        val shoppingList =
            shoppingListDao.findById(id)
                ?: throw KitchenAssistantException(ErrorCode.SHOPPING_LIST_NOT_FOUND)

        val item =
            shoppingList.items.firstOrNull { it.id == itemId }
                ?: throw KitchenAssistantException(ErrorCode.SHOPPING_LIST_ITEM_NOT_FOUND)

        item.name = request.name
        item.quantity = request.quantity
        item.sequence = request.sequence
        item.recipe = request.recipeId?.let { recipeDao.findById(it) }

        return SuccessResponse(shoppingListItemDao.save(item).id)

    }

    fun markAsDone(id: Int, itemId: Int, isCompleted: Boolean): SuccessResponse {
        val shoppingList =
            shoppingListDao.findById(id)
                ?: throw KitchenAssistantException(ErrorCode.SHOPPING_LIST_NOT_FOUND)

        val shoppingListItem = shoppingList.items.firstOrNull { it.id == itemId }
            ?: throw KitchenAssistantException(ErrorCode.SHOPPING_LIST_ITEM_NOT_FOUND)
        shoppingListItem.completed = isCompleted

        shoppingListItemDao.save(shoppingListItem)

        return SuccessResponse(shoppingListItem.id)
    }

    fun delete(id: Int, itemId: Int): SuccessResponse {
        shoppingListDao.findById(id)?.let { shoppingList ->
            shoppingList.items.removeIf { it.id == itemId }
            shoppingListDao.save(shoppingList)
        }
        return SuccessResponse(itemId)
    }

}