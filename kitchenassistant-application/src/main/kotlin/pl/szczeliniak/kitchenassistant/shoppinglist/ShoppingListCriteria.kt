package pl.szczeliniak.kitchenassistant.shoppinglist

import java.time.LocalDate

data class ShoppingListCriteria(
    val userId: Int?,
    val archived: Boolean?,
    val name: String?,
    val date: LocalDate?
)