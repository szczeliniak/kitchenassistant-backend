package pl.szczeliniak.kitchenassistant.shoppinglist

import org.hibernate.validator.constraints.Length
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListCriteria
import pl.szczeliniak.kitchenassistant.shoppinglist.dto.request.NewShoppingListItemRequest
import pl.szczeliniak.kitchenassistant.shoppinglist.dto.request.NewShoppingListRequest
import pl.szczeliniak.kitchenassistant.shoppinglist.dto.request.UpdateShoppingListItemRequest
import pl.szczeliniak.kitchenassistant.shoppinglist.dto.request.UpdateShoppingListRequest
import pl.szczeliniak.kitchenassistant.shoppinglist.dto.response.ShoppingListResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.dto.response.ShoppingListsResponse
import java.time.LocalDate
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
@RequestMapping("/shoppinglists")
@Validated
class ShoppingListController(
    private val shoppingListService: ShoppingListService,
    private val shoppingListItemService: ShoppingListItemService
) {

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ShoppingListResponse {
        return shoppingListService.find(id)
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
        return shoppingListService.findAll(
            page,
            limit,
            ShoppingListCriteria(userId, archived, name, date, recipeId)
        )
    }

    @Transactional
    @PostMapping
    fun add(@Valid @RequestBody request: NewShoppingListRequest): SuccessResponse {
        return shoppingListService.add(request)
    }

    @Transactional
    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @Valid @RequestBody request: UpdateShoppingListRequest): SuccessResponse {
        return shoppingListService.update(id, request)
    }

    @Transactional
    @PostMapping("/{id}/archived/{isArchived}")
    fun archive(@PathVariable id: Int, @PathVariable isArchived: Boolean): SuccessResponse {
        return shoppingListService.archive(id, isArchived)
    }

    @Transactional
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): SuccessResponse {
        return shoppingListService.delete(id)
    }

    @Transactional
    @PostMapping("/{id}/items")
    fun addItem(
        @PathVariable id: Int,
        @Valid @RequestBody request: NewShoppingListItemRequest
    ): SuccessResponse {
        return shoppingListItemService.add(id, request)
    }

    @Transactional
    @PutMapping("/{id}/items/{itemId}")
    fun updateItem(
        @PathVariable id: Int,
        @PathVariable itemId: Int,
        @Valid @RequestBody request: UpdateShoppingListItemRequest
    ): SuccessResponse {
        return shoppingListItemService.update(id, itemId, request)
    }

    @Transactional
    @PostMapping("/{id}/items/{itemId}/completed/{isCompleted}")
    fun markItemAsDone(
        @PathVariable id: Int, @PathVariable itemId: Int, @PathVariable isCompleted: Boolean
    ): SuccessResponse {
        return shoppingListItemService.markAsDone(id, itemId, isCompleted)
    }

    @Transactional
    @DeleteMapping("/{id}/items/{itemId}")
    fun deleteItem(@PathVariable id: Int, @PathVariable itemId: Int): SuccessResponse {
        return shoppingListItemService.delete(id, itemId)
    }

}