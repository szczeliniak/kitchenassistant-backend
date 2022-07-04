package pl.szczeliniak.kitchenassistant.shoppinglist

import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.*
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListDto
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListItemDto
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.UpdateShoppingListDto
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.UpdateShoppingListItemDto
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.GetShoppingListQuery
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.GetShoppingListsQuery
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListsResponse

open class ShoppingListFacadeImpl(
    private val getShoppingListQuery: GetShoppingListQuery,
    private val getShoppingListsQuery: GetShoppingListsQuery,
    private val addShoppingListCommand: AddShoppingListCommand,
    private val updateShoppingListCommand: UpdateShoppingListCommand,
    private val addShoppingListItemCommand: AddShoppingListItemCommand,
    private val updateShoppingListItemCommand: UpdateShoppingListItemCommand,
    private val markItemAsCompletedCommand: MarkItemAsCompletedCommand,
    private val markShoppingListAsArchivedCommand: MarkShoppingListAsArchivedCommand,
    private val deleteShoppingListCommand: DeleteShoppingListCommand,
    private val deleteShoppingListItemCommand: DeleteShoppingListItemCommand,
    private val deassignReceiptFromShoppingListsCommand: DeassignReceiptFromShoppingListsCommand
) : ShoppingListFacade {

    override fun getShoppingList(id: Int): ShoppingListResponse {
        return getShoppingListQuery.execute(id)
    }

    override fun getShoppingLists(page: Long?, limit: Int?, criteria: ShoppingListCriteria): ShoppingListsResponse {
        return getShoppingListsQuery.execute(page, limit, criteria)
    }

    override fun addShoppingList(dto: NewShoppingListDto): SuccessResponse {
        return addShoppingListCommand.execute(dto)
    }

    override fun updateShoppingList(id: Int, dto: UpdateShoppingListDto): SuccessResponse {
        return updateShoppingListCommand.execute(id, dto)
    }

    override fun markShoppingListAsArchived(id: Int, isArchived: Boolean): SuccessResponse {
        return markShoppingListAsArchivedCommand.execute(id, isArchived)
    }

    override fun deleteShoppingList(id: Int): SuccessResponse {
        return deleteShoppingListCommand.execute(id)
    }

    override fun addShoppingListItem(id: Int, dto: NewShoppingListItemDto): SuccessResponse {
        return addShoppingListItemCommand.execute(id, dto)
    }

    override fun updateShoppingListItem(id: Int, itemId: Int, dto: UpdateShoppingListItemDto): SuccessResponse {
        return updateShoppingListItemCommand.execute(id, itemId, dto)
    }

    override fun markShoppingListItemAsDone(id: Int, itemId: Int, isCompleted: Boolean): SuccessResponse {
        return markItemAsCompletedCommand.execute(id, itemId, isCompleted)
    }

    override fun deleteShoppingListItem(id: Int, itemId: Int): SuccessResponse {
        return deleteShoppingListItemCommand.execute(id, itemId)
    }

    override fun deassignReceiptFromShoppingLists(receiptId: Int): SuccessResponse {
        return deassignReceiptFromShoppingListsCommand.execute(receiptId)
    }

}