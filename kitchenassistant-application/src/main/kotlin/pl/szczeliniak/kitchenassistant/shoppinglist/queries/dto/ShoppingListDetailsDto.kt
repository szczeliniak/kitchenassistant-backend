package pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto

import java.time.LocalDate

data class ShoppingListDetailsDto(
    val id: Int,
    val name: String,
    val description: String?,
    val date: LocalDate?,
    val items: Set<ShoppingListItemDto>
)