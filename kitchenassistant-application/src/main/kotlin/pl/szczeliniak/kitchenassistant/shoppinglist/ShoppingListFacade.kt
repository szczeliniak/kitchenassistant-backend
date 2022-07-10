package pl.szczeliniak.kitchenassistant.shoppinglist

import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListDto
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListItemDto
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.UpdateShoppingListDto
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.UpdateShoppingListItemDto
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListsResponse

interface ShoppingListFacade {

    fun getShoppingList(id: Int): ShoppingListResponse
    fun getShoppingLists(page: Long?, limit: Int?, criteria: ShoppingListCriteria): ShoppingListsResponse
    fun addShoppingList(dto: NewShoppingListDto): SuccessResponse
    fun updateShoppingList(id: Int, dto: UpdateShoppingListDto): SuccessResponse
    fun markShoppingListAsArchived(id: Int, isArchived: Boolean): SuccessResponse
    fun deleteShoppingList(id: Int): SuccessResponse
    fun addShoppingListItem(id: Int, dto: NewShoppingListItemDto): SuccessResponse
    fun updateShoppingListItem(id: Int, itemId: Int, dto: UpdateShoppingListItemDto): SuccessResponse
    fun markShoppingListItemAsDone(id: Int, itemId: Int, isCompleted: Boolean): SuccessResponse
    fun deleteShoppingListItem(id: Int, itemId: Int): SuccessResponse
    fun deassignReceiptFromShoppingLists(receiptId: Int): SuccessResponse
    fun triggerArchiving()

}