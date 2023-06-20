package pl.szczeliniak.kitchenassistant.shoppinglist

import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.*
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListRequest
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListItemRequest
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.UpdateShoppingListRequest
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.UpdateShoppingListItemRequest
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListCriteria
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.GetShoppingListQuery
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.GetShoppingListsQuery
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListsResponse

open class ShoppingListFacade(
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
    private val archiveShoppingListsCommand: ArchiveShoppingListsCommand
) {

    fun findById(id: Int): ShoppingListResponse {
        return getShoppingListQuery.execute(id)
    }

    fun findAll(page: Long?, limit: Int?, criteria: ShoppingListCriteria): ShoppingListsResponse {
        return getShoppingListsQuery.execute(page, limit, criteria)
    }

    fun add(request: NewShoppingListRequest): SuccessResponse {
        return addShoppingListCommand.execute(request)
    }

    fun update(id: Int, request: UpdateShoppingListRequest): SuccessResponse {
        return updateShoppingListCommand.execute(id, request)
    }

    fun archive(id: Int, isArchived: Boolean): SuccessResponse {
        return markShoppingListAsArchivedCommand.execute(id, isArchived)
    }

    fun delete(id: Int): SuccessResponse {
        return deleteShoppingListCommand.execute(id)
    }

    fun addShoppingListItem(id: Int, request: NewShoppingListItemRequest): SuccessResponse {
        return addShoppingListItemCommand.execute(id, request)
    }

    fun updateShoppingListItem(id: Int, itemId: Int, request: UpdateShoppingListItemRequest): SuccessResponse {
        return updateShoppingListItemCommand.execute(id, itemId, request)
    }

    fun markShoppingListItemAsDone(id: Int, itemId: Int, isCompleted: Boolean): SuccessResponse {
        return markItemAsCompletedCommand.execute(id, itemId, isCompleted)
    }

    fun deleteShoppingListItem(id: Int, itemId: Int): SuccessResponse {
        return deleteShoppingListItemCommand.execute(id, itemId)
    }

    fun triggerArchiving() {
        archiveShoppingListsCommand.execute()
    }

}