package pl.szczeliniak.kitchenassistant.shoppinglist

import org.hibernate.validator.constraints.Length
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListDto
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListItemDto
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.UpdateShoppingListDto
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.UpdateShoppingListItemDto
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListsResponse
import java.time.LocalDate
import javax.validation.Valid

@RestController
@RequestMapping("/shoppinglists")
@Validated
class ShoppingListController(
    private val shoppingListFacade: ShoppingListFacade
) {

    @GetMapping("/{id}")
    fun getShoppingList(@PathVariable id: Int): ShoppingListResponse {
        return shoppingListFacade.getShoppingList(id)
    }

    @GetMapping
    fun getShoppingLists(
        @RequestParam(required = false) userId: Int?,
        @RequestParam(required = false) archived: Boolean?,
        @RequestParam(required = false) @Length(max = 50) name: String?,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate?,
        @RequestParam(required = false) receiptId: Int?,
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) limit: Int?,
    ): ShoppingListsResponse {
        return shoppingListFacade.getShoppingLists(
            page,
            limit,
            ShoppingListCriteria(userId, archived, name, date, receiptId)
        )
    }

    @PostMapping
    fun addShoppingList(@Valid @RequestBody dto: NewShoppingListDto): SuccessResponse {
        return shoppingListFacade.addShoppingList(dto)
    }

    @PutMapping("/{id}")
    fun updateShoppingList(@PathVariable id: Int, @Valid @RequestBody dto: UpdateShoppingListDto): SuccessResponse {
        return shoppingListFacade.updateShoppingList(id, dto)
    }

    @PostMapping("/{id}/archived/{isArchived}")
    fun markShoppingListAsArchived(@PathVariable id: Int, @PathVariable isArchived: Boolean): SuccessResponse {
        return shoppingListFacade.markShoppingListAsArchived(id, isArchived)
    }

    @DeleteMapping("/{id}")
    fun deleteShoppingList(@PathVariable id: Int): SuccessResponse {
        return shoppingListFacade.deleteShoppingList(id)
    }

    @PostMapping("/{id}/items")
    fun addShoppingListItem(@PathVariable id: Int, @Valid @RequestBody dto: NewShoppingListItemDto): SuccessResponse {
        return shoppingListFacade.addShoppingListItem(id, dto)
    }

    @PutMapping("/{id}/items/{itemId}")
    fun updateShoppingListItem(
        @PathVariable id: Int,
        @PathVariable itemId: Int,
        @Valid @RequestBody dto: UpdateShoppingListItemDto
    ): SuccessResponse {
        return shoppingListFacade.updateShoppingListItem(id, itemId, dto)
    }

    @PostMapping("/{id}/items/{itemId}/completed/{isCompleted}")
    fun markShoppingListItemAsDone(
        @PathVariable id: Int, @PathVariable itemId: Int, @PathVariable isCompleted: Boolean
    ): SuccessResponse {
        return shoppingListFacade.markShoppingListItemAsDone(id, itemId, isCompleted)
    }

    @DeleteMapping("/{id}/items/{itemId}")
    fun deleteShoppingListItem(@PathVariable id: Int, @PathVariable itemId: Int): SuccessResponse {
        return shoppingListFacade.deleteShoppingListItem(id, itemId)
    }

}