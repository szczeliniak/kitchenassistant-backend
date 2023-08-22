package pl.szczeliniak.kitchenassistant.shoppinglist

import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.PaginationUtils
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shared.dtos.Page
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingList
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListCriteria
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListItem
import pl.szczeliniak.kitchenassistant.shoppinglist.dto.request.NewShoppingListRequest
import pl.szczeliniak.kitchenassistant.shoppinglist.dto.request.UpdateShoppingListRequest
import pl.szczeliniak.kitchenassistant.shoppinglist.dto.response.ShoppingListResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.dto.response.ShoppingListsResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.mapper.ShoppingListMapper
import pl.szczeliniak.kitchenassistant.user.db.UserDao

open class ShoppingListService(
    private val shoppingListMapper: ShoppingListMapper,
    private val shoppingListDao: ShoppingListDao,
    private val recipeDao: RecipeDao,
    private val userDao: UserDao,
    private val requestContext: RequestContext
) {

    fun find(id: Int): ShoppingListResponse {
        return ShoppingListResponse(
            shoppingListMapper.mapDetails(
                shoppingListDao.findById(id) ?: throw KitchenAssistantException(ErrorCode.SHOPPING_LIST_NOT_FOUND)
            )
        )
    }

    fun findAll(page: Long?, limit: Int?, criteria: ShoppingListCriteria): ShoppingListsResponse {
        val currentPage = PaginationUtils.calculatePageNumber(page)
        val currentLimit = PaginationUtils.calculateLimit(limit)
        val offset = PaginationUtils.calculateOffset(currentPage, currentLimit)
        val totalNumberOfPages = PaginationUtils.calculateNumberOfPages(currentLimit, shoppingListDao.count(criteria))
        return ShoppingListsResponse(
            Page(
                currentPage, currentLimit, totalNumberOfPages,
                shoppingListDao.findAll(criteria, offset, currentLimit)
                    .map { shoppingListMapper.map(it) }.toSet()
            )
        )
    }

    fun add(request: NewShoppingListRequest): SuccessResponse {
        return SuccessResponse(shoppingListDao.save(createShoppingList(request)).id)
    }

    private fun createShoppingList(request: NewShoppingListRequest): ShoppingList {
        return ShoppingList(
            user = userDao.findById(requestContext.requireUserId())
                ?: throw KitchenAssistantException(ErrorCode.USER_NOT_FOUND),
            name = request.name,
            description = request.description,
            date = request.date,
            items = request.items.map { createShoppingListItem(it) }.toMutableSet(),
        )
    }

    private fun createShoppingListItem(request: NewShoppingListRequest.NewShoppingListItemRequest): ShoppingListItem {
        return ShoppingListItem(
            name = request.name,
            quantity = request.quantity,
            sequence = request.sequence,
            recipe = request.recipeId?.let {
                recipeDao.findById(it) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
            }
        )
    }

    fun update(id: Int, request: UpdateShoppingListRequest): SuccessResponse {
        val shoppingList =
            shoppingListDao.findById(id) ?: throw KitchenAssistantException(ErrorCode.SHOPPING_LIST_NOT_FOUND)
        shoppingList.name = request.name
        shoppingList.description = request.description
        shoppingList.date = request.date
        return SuccessResponse(shoppingListDao.save(shoppingList).id)
    }

    fun archive(id: Int, isArchived: Boolean): SuccessResponse {
        val shoppingList =
            shoppingListDao.findById(id)
                ?: throw KitchenAssistantException(ErrorCode.SHOPPING_LIST_NOT_FOUND)
        shoppingList.archived = isArchived
        shoppingListDao.save(shoppingList)
        return SuccessResponse(shoppingList.id)
    }

    fun delete(id: Int): SuccessResponse {
        shoppingListDao.findById(id)?.let {
            shoppingListDao.delete(it)
        }
        return SuccessResponse(id)
    }

}