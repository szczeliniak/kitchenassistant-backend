package pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto

import org.hibernate.validator.constraints.Length
import java.time.LocalDate
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.Size

data class NewShoppingListDto(
    @Min(1) var userId: Int = 0,
    @field:Length(min = 1, max = 100) var name: String = "",
    @field:Length(min = 1, max = 1000) var description: String? = null,
    var date: LocalDate? = null,
    @field:Size(min = 0, max = 30) var items: Set<@Valid NewShoppingListItemDto> = setOf(),
)