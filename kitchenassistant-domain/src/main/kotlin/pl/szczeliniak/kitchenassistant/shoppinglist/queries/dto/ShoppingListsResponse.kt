package pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto

import pl.szczeliniak.kitchenassistant.shared.dtos.Pagination

data class ShoppingListsResponse(
    val shoppingLists: Set<ShoppingListDto>,
    val pagination: Pagination
)