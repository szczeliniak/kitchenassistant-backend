package pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto

import java.time.LocalDate

data class UpdateShoppingListDto(
    var name: String = "",
    var description: String? = null,
    var date: LocalDate? = null
)