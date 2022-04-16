package pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto

import org.hibernate.validator.constraints.Length
import java.time.LocalDate
import javax.validation.Valid
import javax.validation.constraints.Min

data class NewShoppingListDto(
    @Min(1) var userId: Int = 0,
    @field:Length(min = 1, max = 100) var name: String = "",
    @field:Length(min = 1, max = 1000) var description: String? = null,
    var date: LocalDate? = null,
    var items: List<@Valid NewShoppingListItemDto> = mutableListOf(),
)