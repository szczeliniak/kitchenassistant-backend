package pl.szczeliniak.kitchenassistant.shoppinglist

import org.hibernate.validator.constraints.Length
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.szczeliniak.kitchenassistant.security.AuthorizationService
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListRequest
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListItemRequest
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.UpdateShoppingListRequest
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.UpdateShoppingListItemRequest
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListCriteria
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListsResponse
import java.time.LocalDate
import javax.validation.Valid

@RestController
@RequestMapping("/shoppinglists")
@Validated
class ShoppingListController(
    private val shoppingListFacade: ShoppingListFacade,
    private val authorizationService: AuthorizationService
) {

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ShoppingListResponse {
        authorizationService.checkIsOwnerOfShoppingList(id)
        return shoppingListFacade.findById(id)
    }

    @GetMapping
    fun findAll(
        @RequestParam(required = false) userId: Int?,
        @RequestParam(required = false) archived: Boolean?,
        @RequestParam(required = false) @Length(max = 50) name: String?,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate?,
        @RequestParam(required = false) recipeId: Int?,
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) limit: Int?,
    ): ShoppingListsResponse {
        authorizationService.checkIsOwner(userId)
        return shoppingListFacade.findAll(
            page,
            limit,
            ShoppingListCriteria(userId, archived, name, date, recipeId)
        )
    }

    @PostMapping
    fun add(@Valid @RequestBody request: NewShoppingListRequest): SuccessResponse {
        return shoppingListFacade.add(request)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @Valid @RequestBody request: UpdateShoppingListRequest): SuccessResponse {
        authorizationService.checkIsOwnerOfShoppingList(id)
        return shoppingListFacade.update(id, request)
    }

    @PostMapping("/{id}/archived/{isArchived}")
    fun archive(@PathVariable id: Int, @PathVariable isArchived: Boolean): SuccessResponse {
        authorizationService.checkIsOwnerOfShoppingList(id)
        return shoppingListFacade.archive(id, isArchived)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): SuccessResponse {
        authorizationService.checkIsOwnerOfShoppingList(id)
        return shoppingListFacade.delete(id)
    }

    @PostMapping("/{id}/items")
    fun addShoppingListItem(@PathVariable id: Int, @Valid @RequestBody request: NewShoppingListItemRequest): SuccessResponse {
        authorizationService.checkIsOwnerOfShoppingList(id)
        return shoppingListFacade.addShoppingListItem(id, request)
    }

    @PutMapping("/{id}/items/{itemId}")
    fun updateShoppingListItem(
        @PathVariable id: Int,
        @PathVariable itemId: Int,
        @Valid @RequestBody request: UpdateShoppingListItemRequest
    ): SuccessResponse {
        authorizationService.checkIsOwnerOfShoppingList(id)
        return shoppingListFacade.updateShoppingListItem(id, itemId, request)
    }

    @PostMapping("/{id}/items/{itemId}/completed/{isCompleted}")
    fun markShoppingListItemAsDone(
        @PathVariable id: Int, @PathVariable itemId: Int, @PathVariable isCompleted: Boolean
    ): SuccessResponse {
        authorizationService.checkIsOwnerOfShoppingList(id)
        return shoppingListFacade.markShoppingListItemAsDone(id, itemId, isCompleted)
    }

    @DeleteMapping("/{id}/items/{itemId}")
    fun deleteShoppingListItem(@PathVariable id: Int, @PathVariable itemId: Int): SuccessResponse {
        authorizationService.checkIsOwnerOfShoppingList(id)
        return shoppingListFacade.deleteShoppingListItem(id, itemId)
    }

}