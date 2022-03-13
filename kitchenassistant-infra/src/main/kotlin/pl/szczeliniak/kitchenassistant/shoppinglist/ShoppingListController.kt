package pl.szczeliniak.kitchenassistant.shoppinglist

import org.springframework.web.bind.annotation.*
import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.AddShoppingListCommand
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.AddShoppingListItemCommand
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.DeleteShoppingListCommand
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.DeleteShoppingListItemCommand
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListDto
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListItemDto
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.GetShoppingListQuery
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.GetShoppingListsQuery
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListsResponse

@RestController
@RequestMapping("/shoppinglists")
class ShoppingListController(
    private val getShoppingListQuery: GetShoppingListQuery,
    private val getShoppingListsQuery: GetShoppingListsQuery,
    private val addShoppingListCommand: AddShoppingListCommand,
    private val addShoppingListItemCommand: AddShoppingListItemCommand,
    private val deleteShoppingListCommand: DeleteShoppingListCommand,
    private val deleteShoppingListItemCommand: DeleteShoppingListItemCommand,
) {

    @GetMapping("/{id}")
    fun getShoppingList(@PathVariable id: Int): ShoppingListResponse {
        return getShoppingListQuery.execute(id)
    }

    @GetMapping
    fun getShoppingLists(): ShoppingListsResponse {
        return getShoppingListsQuery.execute(ShoppingListCriteria(null))
    }

    @PostMapping
    fun addShoppingList(@RequestBody dto: NewShoppingListDto): SuccessResponse {
        return addShoppingListCommand.execute(dto)
    }

    @DeleteMapping("/{id}")
    fun deleteShoppingList(@PathVariable id: Int): SuccessResponse {
        return deleteShoppingListCommand.execute(id)
    }

    @PostMapping("/{id}/items")
    fun addShoppingListItem(@PathVariable id: Int, @RequestBody dto: NewShoppingListItemDto): SuccessResponse {
        return addShoppingListItemCommand.execute(id, dto)
    }

    @DeleteMapping("/{id}/items/{itemId}")
    fun deleteShoppingList(@PathVariable id: Int, @PathVariable itemId: Int): SuccessResponse {
        return deleteShoppingListItemCommand.execute(id, itemId)
    }

}