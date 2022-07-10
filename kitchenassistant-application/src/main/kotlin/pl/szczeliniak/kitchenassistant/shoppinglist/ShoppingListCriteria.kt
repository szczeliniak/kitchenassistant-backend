package pl.szczeliniak.kitchenassistant.shoppinglist

import java.time.LocalDate

data class ShoppingListCriteria(
    val userId: Int? = null,
    val archived: Boolean? = null,
    val name: String? = null,
    val date: LocalDate? = null,
    val receiptId: Int? = null,
    val automaticArchiving: Boolean? = null,
    val maxDate: LocalDate? = null
)