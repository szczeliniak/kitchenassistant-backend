package pl.szczeliniak.kitchenassistant.shoppinglist.dto.request

import org.hibernate.validator.constraints.Length
import java.time.LocalDate

data class UpdateShoppingListRequest(
    @field:Length(min = 1, max = 100) var name: String = "",
    @field:Length(max = 1000) var description: String? = null,
    var date: LocalDate? = null,
)