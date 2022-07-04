package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListCriteria
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao

open class DeassignReceiptFromShoppingListsCommand(
    private val shoppingListDao: ShoppingListDao
) {
    open fun execute(receiptId: Int): SuccessResponse {
        val shoppingLists = shoppingListDao.findAll(ShoppingListCriteria(receiptId = receiptId))
        shoppingLists.forEach {
            it.items.filter { item -> item.receiptId == receiptId }.forEach { item -> item.receiptId = null }
        }
        shoppingListDao.save(shoppingLists)
        return SuccessResponse(receiptId)
    }

}