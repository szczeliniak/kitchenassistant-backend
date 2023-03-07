package pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto

import org.hibernate.validator.constraints.Length
import java.time.LocalDate

data class UpdateShoppingListDto(
    @field:Length(min = 1, max = 100) var name: String = "",
    @field:Length(max = 1000) var description: String? = null,
    var date: LocalDate? = null,
    var automaticArchiving: Boolean = false
)