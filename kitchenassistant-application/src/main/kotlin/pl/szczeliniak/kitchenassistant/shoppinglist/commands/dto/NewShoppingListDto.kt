package pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto

import java.time.LocalDate

data class NewShoppingListDto(
    var userId: Int = 0,
    var name: String = "",
    var description: String? = null,
    var date: LocalDate? = null,
    var items: List<NewShoppingListItemDto> = mutableListOf(),
)