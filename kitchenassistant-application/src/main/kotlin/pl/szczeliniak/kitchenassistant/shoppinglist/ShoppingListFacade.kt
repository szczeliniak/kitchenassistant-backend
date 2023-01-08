package pl.szczeliniak.kitchenassistant.shoppinglist

import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListDto
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListItemDto
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.UpdateShoppingListDto
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.UpdateShoppingListItemDto
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListsResponse

interface ShoppingListFacade {

    fun findById(id: Int): ShoppingListResponse
    fun findAll(page: Long?, limit: Int?, criteria: ShoppingListCriteria): ShoppingListsResponse
    fun add(dto: NewShoppingListDto): SuccessResponse
    fun update(id: Int, dto: UpdateShoppingListDto): SuccessResponse
    fun archive(id: Int, isArchived: Boolean): SuccessResponse
    fun delete(id: Int): SuccessResponse
    fun addShoppingListItem(id: Int, dto: NewShoppingListItemDto): SuccessResponse
    fun updateShoppingListItem(id: Int, itemId: Int, dto: UpdateShoppingListItemDto): SuccessResponse
    fun markShoppingListItemAsDone(id: Int, itemId: Int, isCompleted: Boolean): SuccessResponse
    fun deleteShoppingListItem(id: Int, itemId: Int): SuccessResponse
    fun deassignRecipeFromShoppingLists(recipeId: Int): SuccessResponse
    fun triggerArchiving()

}