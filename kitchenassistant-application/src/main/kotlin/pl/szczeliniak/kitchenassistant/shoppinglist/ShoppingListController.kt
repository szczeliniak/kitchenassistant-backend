package pl.szczeliniak.kitchenassistant.shoppinglist

import org.hibernate.validator.constraints.Length
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListDto
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListItemDto
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.UpdateShoppingListDto
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.UpdateShoppingListItemDto
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListCriteria
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

    @PreAuthorize("@authorizationService.isOwnerOfShoppingList(#id)")
    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ShoppingListResponse {
        return shoppingListFacade.findById(id)
    }

    @PreAuthorize("@authorizationService.isOwner(#userId)")
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
        return shoppingListFacade.findAll(
                page,
                limit,
                ShoppingListCriteria(userId, archived, name, date, recipeId)
        )
    }

    @PostMapping
    fun add(@Valid @RequestBody dto: NewShoppingListDto): SuccessResponse {
        return shoppingListFacade.add(dto)
    }

    @PreAuthorize("@authorizationService.isOwnerOfShoppingList(#id)")
    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @Valid @RequestBody dto: UpdateShoppingListDto): SuccessResponse {
        return shoppingListFacade.update(id, dto)
    }

    @PreAuthorize("@authorizationService.isOwnerOfShoppingList(#id)")
    @PostMapping("/{id}/archived/{isArchived}")
    fun archive(@PathVariable id: Int, @PathVariable isArchived: Boolean): SuccessResponse {
        return shoppingListFacade.archive(id, isArchived)
    }

    @PreAuthorize("@authorizationService.isOwnerOfShoppingList(#id)")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): SuccessResponse {
        return shoppingListFacade.delete(id)
    }

    @PreAuthorize("@authorizationService.isOwnerOfShoppingList(#id)")
    @PostMapping("/{id}/items")
    fun addShoppingListItem(@PathVariable id: Int, @Valid @RequestBody dto: NewShoppingListItemDto): SuccessResponse {
        return shoppingListFacade.addShoppingListItem(id, dto)
    }

    @PreAuthorize("@authorizationService.isOwnerOfShoppingList(#id)")
    @PutMapping("/{id}/items/{itemId}")
    fun updateShoppingListItem(
            @PathVariable id: Int,
            @PathVariable itemId: Int,
            @Valid @RequestBody dto: UpdateShoppingListItemDto
    ): SuccessResponse {
        return shoppingListFacade.updateShoppingListItem(id, itemId, dto)
    }

    @PreAuthorize("@authorizationService.isOwnerOfShoppingList(#id)")
    @PostMapping("/{id}/items/{itemId}/completed/{isCompleted}")
    fun markShoppingListItemAsDone(
            @PathVariable id: Int, @PathVariable itemId: Int, @PathVariable isCompleted: Boolean
    ): SuccessResponse {
        return shoppingListFacade.markShoppingListItemAsDone(id, itemId, isCompleted)
    }

    @PreAuthorize("@authorizationService.isOwnerOfShoppingList(#id)")
    @DeleteMapping("/{id}/items/{itemId}")
    fun deleteShoppingListItem(@PathVariable id: Int, @PathVariable itemId: Int): SuccessResponse {
        return shoppingListFacade.deleteShoppingListItem(id, itemId)
    }

}