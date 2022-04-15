package pl.szczeliniak.kitchenassistant.shoppinglist

import org.springframework.web.bind.annotation.*
import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.*
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListDto
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListItemDto
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.UpdateShoppingListDto
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.UpdateShoppingListItemDto
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.GetShoppingListQuery
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.GetShoppingListsQuery
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListsResponse
import java.time.LocalDate

@RestController
@RequestMapping("/shoppinglists")
class ShoppingListController(
    private val getShoppingListQuery: GetShoppingListQuery,
    private val getShoppingListsQuery: GetShoppingListsQuery,
    private val addShoppingListCommand: AddShoppingListCommand,
    private val updateShoppingListCommand: UpdateShoppingListCommand,
    private val addShoppingListItemCommand: AddShoppingListItemCommand,
    private val updateShoppingListItemCommand: UpdateShoppingListItemCommand,
    private val markShoppingListItemAsDoneCommand: MarkShoppingListItemAsDoneCommand,
    private val markShoppingListAsArchivedCommand: MarkShoppingListAsArchivedCommand,
    private val deleteShoppingListCommand: DeleteShoppingListCommand,
    private val deleteShoppingListItemCommand: DeleteShoppingListItemCommand,
) {

    @GetMapping("/{id}")
    fun getShoppingList(@PathVariable id: Int): ShoppingListResponse {
        return getShoppingListQuery.execute(id)
    }

    @GetMapping
    fun getShoppingLists(
        @RequestParam(required = false) userId: Int?,
        @RequestParam(required = false) archived: Boolean?,
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) date: LocalDate?
    ): ShoppingListsResponse {
        return getShoppingListsQuery.execute(ShoppingListCriteria(userId, archived, name, date))
    }

    @PostMapping
    fun addShoppingList(@RequestBody dto: NewShoppingListDto): SuccessResponse {
        return addShoppingListCommand.execute(dto)
    }

    @PutMapping("/{id}")
    fun updateShoppingList(@PathVariable id: Int, @RequestBody dto: UpdateShoppingListDto): SuccessResponse {
        return updateShoppingListCommand.execute(id, dto)
    }

    @PostMapping("/{id}/archived/{isArchived}")
    fun markShoppingListAsArchived(@PathVariable id: Int, @PathVariable isArchived: Boolean): SuccessResponse {
        return markShoppingListAsArchivedCommand.execute(id, isArchived)
    }

    @DeleteMapping("/{id}")
    fun deleteShoppingList(@PathVariable id: Int): SuccessResponse {
        return deleteShoppingListCommand.execute(id)
    }

    @PostMapping("/{id}/items")
    fun addShoppingListItem(@PathVariable id: Int, @RequestBody dto: NewShoppingListItemDto): SuccessResponse {
        return addShoppingListItemCommand.execute(id, dto)
    }

    @PutMapping("/{id}/items/{itemId}")
    fun updateShoppingListItem(
        @PathVariable id: Int,
        @PathVariable itemId: Int,
        @RequestBody dto: UpdateShoppingListItemDto
    ): SuccessResponse {
        return updateShoppingListItemCommand.execute(id, itemId, dto)
    }

    @PostMapping("/{id}/items/{itemId}/done/{isDone}")
    fun markShoppingListItemAsDone(
        @PathVariable id: Int, @PathVariable itemId: Int, @PathVariable isDone: Boolean
    ): SuccessResponse {
        return markShoppingListItemAsDoneCommand.execute(id, itemId, isDone)
    }

    @DeleteMapping("/{id}/items/{itemId}")
    fun deleteShoppingListItem(@PathVariable id: Int, @PathVariable itemId: Int): SuccessResponse {
        return deleteShoppingListItemCommand.execute(id, itemId)
    }

}