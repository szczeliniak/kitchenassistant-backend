package pl.szczeliniak.kitchenassistant.shoppinglist.dto.request

import org.hibernate.validator.constraints.Length
import java.time.LocalDate
import javax.validation.Valid
import javax.validation.constraints.Size

data class NewShoppingListRequest(
    @field:Length(min = 1, max = 100) var name: String = "",
    @field:Length(min = 1, max = 1000) var description: String? = null,
    var date: LocalDate? = null,
    @field:Size(min = 0, max = 30) var items: Set<@Valid NewShoppingListItemRequest> = setOf(),
) {
    data class NewShoppingListItemRequest(
        @field:Length(min = 1, max = 100) var name: String = "",
        @field:Length(min = 1, max = 50) var quantity: String? = null,
        var sequence: Int? = null,
        var recipeId: Int? = null
    )
}