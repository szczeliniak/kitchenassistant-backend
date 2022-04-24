package pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto

import pl.szczeliniak.kitchenassistant.common.dto.Pagination

data class ShoppingListsResponse(
    val shoppingLists: Set<ShoppingListDto>,
    val pagination: Pagination
)