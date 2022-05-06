package pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto

import java.time.LocalDate

data class ShoppingListDto(
    val id: Int,
    val name: String,
    val description: String?,
    val date: LocalDate?,
    val archived: Boolean,
    val items: Set<ShoppingListItemDto>
)