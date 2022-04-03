package pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto

data class NewShoppingListDto(
    var userId: Int = 0,
    var name: String = "",
    var description: String? = null,
    var items: List<NewShoppingListItemDto> = mutableListOf(),
)