package pl.szczeliniak.kitchenassistant.shoppinglist.dto.response

import pl.szczeliniak.kitchenassistant.shared.dtos.Page
import java.time.LocalDate

data class ShoppingListsResponse(
    val shoppingLists: Page<ShoppingListDto>
) {
    data class ShoppingListDto(
        val id: Int,
        val name: String,
        val description: String?,
        val date: LocalDate?
    )
}